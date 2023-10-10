package com.teach.challenge.domain.models.post;

import jakarta.validation.constraints.NotNull;

public record GetAuthorDTO(

        @NotNull(message = "userName não informado")
        Long id
) {


}
