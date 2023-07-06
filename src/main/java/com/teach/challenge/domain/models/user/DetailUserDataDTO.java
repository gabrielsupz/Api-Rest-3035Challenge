package com.teach.challenge.domain.models.user;


import java.time.LocalDate;

public record DetailUserDataDTO(Long id, String userName, String name, String phone, String email, String profileLink, LocalDate createdAt, LocalDate updatedAt
                                    ) {
    public DetailUserDataDTO(User user) {
        this(user.getId(),user.getUserName(), user.getName(), user.getPhone(), user.getEmail(), user.getProfileLink(), user.getCreatedAt(), user.getUpdatedAt());
    }


}
