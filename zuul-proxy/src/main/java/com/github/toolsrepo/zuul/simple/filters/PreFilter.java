package com.github.toolsrepo.zuul.simple.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

public class PreFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PreFilter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        log.info(String.format("Request headers %s", Collections.list(request.getHeaderNames()).stream().collect(Collectors.joining(","))));

        if (!existsAuthnHeader(request, "x-auth-key")) {
            ctx.unset();
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }

    private boolean existsAuthnHeader(HttpServletRequest request, String authnKey) {
        String headerValue = request.getHeader(authnKey);
        log.info(String.format("headerValue is %s and is not empty %s", headerValue, !StringUtils.isEmpty(headerValue)));
        return !StringUtils.isEmpty(headerValue);
    }
}
