package com.teach.challenge.domain.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UpdateUserDataDTO(

        String userName,
        @Pattern(regexp = "\\d{5,20}")
        String password,
        String name,

        String phone,
        @Email(message = "Formato de email incorreto")
        String email,



        String profileLink,
        String description




) {
}
