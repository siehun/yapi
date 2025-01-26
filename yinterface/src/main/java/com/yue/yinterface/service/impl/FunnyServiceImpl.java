package com.yue.yinterface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yue.yinterface.domain.Funny;
import com.yue.yinterface.mapper.FunnyMapper;
import com.yue.yinterface.service.FunnyService;
import org.springframework.stereotype.Service;

/**
* @author 东行
* @description 针对表【funny(笑话表)】的数据库操作Service实现
* @createDate 2025-01-26 11:58:30
*/
@Service
public class FunnyServiceImpl extends ServiceImpl<FunnyMapper, Funny>
    implements FunnyService {

    @Override
    public Funny getFunny() {
        long count = this.count();
        if (count == 0) {
            return null;
        }
        int id = (int) (Math.random() * (int)count) + 1;
        return this.getById(id);

    }
}




