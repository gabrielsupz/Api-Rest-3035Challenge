package com.teach.challenge.domain.models.post;

import jakarta.validation.constraints.NotNull;

public record GetAuthorDTO(

        @NotNull(message = "Id não informado")
        Long id
) {


}
