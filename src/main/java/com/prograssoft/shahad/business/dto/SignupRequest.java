package com.prograssoft.shahad.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Size(min=4) String username,
        @NotBlank @Size(min=6) String password
) {
}