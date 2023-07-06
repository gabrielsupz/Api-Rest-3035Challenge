package com.teach.challenge.domain.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDataDTO(
        @NotNull(message = "UserName não informado")
        String userName,
        @NotBlank(message = "Senha não pode estar vazia")

        String password,
        @NotNull(message = "Nome não informado")
        String name,
        @NotNull(message = "Telefone não informado")

        String phone,
        @NotBlank(message = "Email não informado")
        @Email(message = "Formato de email incorreto")
        String email,


        @NotBlank(message = "URL da Imagem do Usuário não informada")

        String profileLink
) {


}
