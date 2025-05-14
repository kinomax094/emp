package com.karoi.spalek.emp.dto;

import jakarta.validation.constraints.NotBlank;

public record ClaimantDto(
        @NotBlank(message = "Identifier cannot be blank")
        String identifier,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Surname cannot be blank")
        String surname,
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "PhoneNumber cannot be blank")
        String phoneNumber
) {
}
