package com.teach.challenge.infra.security.DTOs;

import jakarta.validation.constraints.NotBlank;

public record LoginDataDTO(
        @NotBlank(message = "login não informado")
        String userName,

        @NotBlank(message = "senha não informada")

        String password
){


}
