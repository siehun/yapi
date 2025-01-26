package com.yue.yinterface.controller;

import com.yue.yinterface.common.BaseResponse;
import com.yue.yinterface.common.ResultUtils;
import com.yue.yinterface.domain.Ancientpoetry;
import com.yue.yinterface.domain.Funny;
import com.yue.yinterface.service.AncientpoetryService;
import com.yue.yinterface.service.FunnyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Internet")
public class InternetController {
    @Autowired
    private AncientpoetryService ancientpoetryService;
    @Autowired
    private FunnyService funnyService;
    @GetMapping("/poetry")
    public BaseResponse<Ancientpoetry> getPoetry() {
        return ResultUtils.success(ancientpoetryService.getPoety());
    }
    @GetMapping("/funny")
    public BaseResponse<Funny> getFunny() {
        return ResultUtils.success(funnyService.getFunny());
    }


}
