package com.yue.yinterface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.yinterface.domain.Person;
import com.yue.yinterface.mapper.PersonMapper;
import com.yue.yinterface.service.PersonService;
import com.yue.yinterface.utils.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public String getPerson() {
        Person p = cacheClient.queryWithMutex("person", 1, Person.class, this::getById, 9L, TimeUnit.MINUTES);
//        int hour = LocalDateTime.now().getHour();
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("hour", 1);
//        Person p = this.getOne(queryWrapper);
        return p.getMessage();
    }
}
