package com.karoi.spalek.emp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddProductComplaintDto(
        @NotBlank(message = "Identifier cannot be blank")
        String identifier,
        @Size(min = 30, max = 500, message = "Wrong content size")
        String content,
        @NotNull(message = "Claimant cannot be null")
        ClaimantDto claimant
) {
}
