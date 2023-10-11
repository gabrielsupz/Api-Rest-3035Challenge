package com.teach.challenge.domain.models.user;

import jakarta.validation.constraints.NotNull;

public record UserIsFriendDTO(

        @NotNull(message = "Id não informado")
        Long id
) {


}
