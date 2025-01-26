package com.yue.yinterface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.yinterface.domain.Ancientpoetry;
import com.yue.yinterface.mapper.AncientpoetryMapper;
import com.yue.yinterface.service.AncientpoetryService;
import org.springframework.stereotype.Service;

/**
* @author 东行
* @description 针对表【ancientpoetry(古诗表)】的数据库操作Service实现
* @createDate 2025-01-26 09:39:53
*/
@Service
public class AncientpoetryServiceImpl extends ServiceImpl<AncientpoetryMapper, Ancientpoetry>
    implements AncientpoetryService {

    @Override
    public Ancientpoetry getPoety() {
        long count = this.count();
        if (count == 0) {
            return null;
        }
        int id = (int)(Math.random() * (int)count) + 1;
        return this.getById(id);
    }
}




