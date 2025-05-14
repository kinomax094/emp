package com.karoi.spalek.emp.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class RequestData {
    private String ipAddress;
}