package com.yue.yinterface.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.yinterface.domain.Funny;

/**
* @author 东行
* @description 针对表【funny(笑话表)】的数据库操作Service
* @createDate 2025-01-26 11:58:30
*/
public interface FunnyService extends IService<Funny> {

    Funny getFunny();
}
