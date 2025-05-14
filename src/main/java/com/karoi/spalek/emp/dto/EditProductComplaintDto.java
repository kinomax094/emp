package com.karoi.spalek.emp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditProductComplaintDto(
        @NotBlank(message = "Identifier cannot be null")
        String identifier,
        @Size(min = 30, max = 500, message = "Wrong content size")
        String content
) {
}
