package com.teach.challenge.domain.models.post;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataPostDataDTO(


        @NotNull(message = "Não informado a visibilidade do post")
        Boolean postIsPrivate,
        @NotBlank(message = "Titúlo não informado")
        String title,
        @NotBlank(message = "Sem descrição informada")
        String description,


        String photoLink,


        String videoLink


) {
}
