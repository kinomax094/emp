package com.karoi.spalek.emp.filter;

import com.karoi.spalek.emp.context.RequestData;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProductComplainFilter implements Filter {

    private final RequestData requestData;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        requestData.setIpAddress(getClientIp(httpRequest));
        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip != null) ? ip.split(",")[0].trim() : request.getRemoteAddr();
    }
}
