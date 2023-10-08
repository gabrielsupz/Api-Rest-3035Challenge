package com.teach.challenge.domain.models.user;

import jakarta.validation.constraints.NotNull;

public record FriendUserDataDTO(

        @NotNull(message = "id não informado")
        Long id
) {


}
