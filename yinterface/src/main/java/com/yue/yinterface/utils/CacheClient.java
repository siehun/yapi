package com.yue.yinterface.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Component
public class CacheClient {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    // 拿锁
    private boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    // 解锁
    private void unLock(String key) {
        redisTemplate.delete(key);
    }

    // 设置key, value, 真实过期
    public void set(String key, Object value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }
    // 设置key, value, 逻辑过期
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 构造数据
        RedisData data = new RedisData();
        data.setData(value);
        data.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 存入redis
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(data));
    }
    // 缓存穿透, 缓存空对象
    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        //先去redis查询缓存
        String key = keyPrefix + id;
        String json = redisTemplate.opsForValue().get(key);
        // 如果存在
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        // 判断返回的是否为空值
        if (json != null) {
            return null;
        }
        // 不存在，查mysql
        R r = dbFallback.apply(id);
        // 不存在，返回错误
        if (r == null) {
            redisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        //存在，存在redis,返回
        this.set(key, r, time, unit);
        return r;
    }
    // 缓存击穿，逻辑过期
    public <R,ID> R queryWithLogicalExpire(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        //先去redis查询缓存
        String key = keyPrefix + id;
        String shopJson = redisTemplate.opsForValue().get(key);
        // 如果存在
        if (StrUtil.isBlank(shopJson)) {
            // 不命中，返回空
            return null;
        }
        RedisData redisData = JSONUtil.toBean(shopJson, RedisData.class);
        // 注意这里不能直接强转，因为拿到的是实际是jsonObject对象
        R r = JSONUtil.toBean((JSONObject)redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 判断是否过期
        // 未过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            return r;
        }
        // 过期, 缓存重建
        String lockKey = RedisConstants.LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        if (isLock) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    R newR = dbFallback.apply(id);
                    this.setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    unLock(lockKey);
                }
            });
        }
        return r;
    }
    // 缓存击穿， 互斥锁
    public <R, ID> R queryWithMutex(String keyPrefix, ID id, Class<R> type, Function<ID,R> dbFallback, Long time, TimeUnit unit) {
        //先去redis查询缓存
        String key = keyPrefix + id;
        String shopJson = redisTemplate.opsForValue().get(key);
        // 如果存在
        if (StrUtil.isNotBlank(shopJson)) {
            return JSONUtil.toBean(shopJson, type);
        }
        if (shopJson != null) {
            return null;
        }
        // 实现缓存重构
        String lockKey = RedisConstants.LOCK_SHOP_KEY + id;
        R r = null;
        try {
            // 获得互斥锁
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, id, type, dbFallback, time, unit);
            }
            // 获取成功，查询数据库
            r = dbFallback.apply(id);
            //
            if (r == null) {
                redisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            //存在，存在redis,返回
            this.set(key, r, time, unit);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            unLock(lockKey);
        }
        return r;
    }
}
