package com.teach.challenge.domain.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterUserDataDTO(
        @NotNull(message = "UserName não informado")
        String userName,
        @NotBlank(message = "Senha não pode estar vazia")
        @Pattern(regexp = "\\d{3,20}", message = "Senha deve ter entre 3 e 20 digitos")
        String password,
        @NotNull(message = "Nome não informado")
        String name,
        @NotNull(message = "Telefone não informado")

        @NotNull(message = "Telefone não informado")
        @Pattern(regexp = "\\d{8,}", message = "Telefone deve ter no mínimo 6 dígitos")
        String phone,
        @NotBlank(message = "Email não informado")
        @Email(message = "Formato de email incorreto")
        String email,


        @NotBlank(message = "URL da Imagem do Usuário não informada")

        String profileLink,

        @NotBlank(message = "Descrição do usuário não informada")
        String description
) {


}
