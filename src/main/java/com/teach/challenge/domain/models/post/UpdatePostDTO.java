package com.teach.challenge.domain.models.post;

import jakarta.validation.constraints.NotNull;

public record UpdatePostDTO(
        @NotNull(message = "Id do post não informado")
        Long id,
        String title,
        String description,
        String photoLink,
        String videoLink,
        Boolean postIsPrivate


) {

}
