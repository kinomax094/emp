package com.karoi.spalek.emp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ProductComplaintDto(
        String identifier,
        String content,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime createDate,
        int upDateCount,
        ClaimantDto claimant
) {
}
