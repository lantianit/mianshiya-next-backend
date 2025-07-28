package com.yupi.mianshiya.blackfilter;

import com.yupi.mianshiya.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局 IP 黑名单过滤请求拦截器
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "blackIpFilter")
public class BlackIpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String ipAddress = NetUtils.getIpAddress((HttpServletRequest) servletRequest);
        log.info("黑名单过滤器检查IP: {}", ipAddress);
        
        // 处理CORS预检请求
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            log.debug("处理CORS预检请求，允许通过");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        if (BlackIpUtils.isBlackIp(ipAddress)) {
            log.warn("IP {} 命中黑名单，拒绝访问", ipAddress);
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            
            // 不在这里设置CORS头，让CorsConfig统一处理
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(403); // 设置HTTP状态码为403 Forbidden
            
            String errorResponse = "{\"errorCode\":\"-1\",\"errorMsg\":\"黑名单IP，禁止访问\",\"code\":-1,\"message\":\"您的IP地址已被加入黑名单，暂时无法访问系统\"}";
            log.info("返回黑名单错误响应: {}", errorResponse);
            httpResponse.getWriter().write(errorResponse);
            return;
        }
        
        log.debug("IP {} 通过黑名单检查，允许访问", ipAddress);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}