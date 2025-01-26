package com.yue.yinterface.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class FlowInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器");
        String flow = request.getHeader("gateway");
        log.info("校验流量{}",flow);
        if (StrUtil.isBlank(flow)) {
            return false;
        }
        if (flow.equals("yue")) {
            return true;
        } else {
            return false;
        }
    }
}
