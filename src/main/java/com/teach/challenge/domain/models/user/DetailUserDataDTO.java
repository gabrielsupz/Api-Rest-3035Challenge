package com.teach.challenge.domain.models.user;


import java.time.LocalDate;

public record DetailUserDataDTO(Long id, String userName, String name, String phone, String email, String profileLink,String description, LocalDate createdAt, LocalDate updatedAt, Long friendsCount,  Long postsCount
                                    ) {
    public DetailUserDataDTO(User user) {
        this(user.getId(),user.getUserName(), user.getName(), user.getPhone(), user.getEmail(), user.getProfileLink(), user.getDescription(),user.getCreatedAt(), user.getUpdatedAt(), user.getActiveFriendCount(), user.getActivePostCount());
    }



}
