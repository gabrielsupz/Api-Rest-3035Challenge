package com.teach.challenge.domain.models.post;


import java.time.LocalDate;

public record DetailPostDataDTO(

String author,
        String authorPhoto,
        Long authorId,
        Long id,
        String title,
        String description,
        String photoLink,
        String videoLink,
        LocalDate createdAt,
        LocalDate updatedAt,

Integer likes,

Boolean postIsPrivate
                                    ) {
    public DetailPostDataDTO(Post p) {
        this(p.getPostCreator().getUserName(), p.getPostCreator().getProfileLink(),p.getPostCreator().getId(), p.getId(), p.getTitle(), p.getDescription(), p.getPhotoLink(), p.getVideoLink(), p.getCreatedAt(), p.getUpdatedAt(),p.getLikes(), p.getPostIsPrivate());
    }


}
