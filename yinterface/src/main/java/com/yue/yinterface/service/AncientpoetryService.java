package com.yue.yinterface.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.yinterface.domain.Ancientpoetry;

/**
* @author 东行
* @description 针对表【ancientpoetry(古诗表)】的数据库操作Service
* @createDate 2025-01-26 09:39:53
*/
public interface AncientpoetryService extends IService<Ancientpoetry> {

    Ancientpoetry getPoety();
}
