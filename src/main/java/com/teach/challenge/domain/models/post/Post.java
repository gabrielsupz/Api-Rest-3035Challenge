package com.teach.challenge.domain.models.post;

import com.teach.challenge.domain.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String title;
    private String description;
    private String photoLink;
    private String videoLink;
    private Boolean postIsPrivate;
private Integer likes;
    @ManyToOne( fetch = FetchType.LAZY)
    private User postCreator;

    public Post(@NotNull DataPostDataDTO d, User usuario) {
        this.deleted = false;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.title = d.title();
        this.description = d.description();
        this.likes = 0;
        if(!d.photoLink().isEmpty()){
            this.photoLink = d.photoLink();
        }
        if(!d.videoLink().isEmpty()){
            this.videoLink = d.videoLink();
        }
        this.postIsPrivate = d.postIsPrivate();
        this.postCreator = usuario;


    }


    public void updateData(UpdatePostDTO dados) {

        if (!(dados.postIsPrivate() == null)) {
            this.postIsPrivate = dados.postIsPrivate();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.description() == null)) {
            this.description = dados.description();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.title() == null)) {
            this.title = dados.title();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.videoLink() == null)) {
            this.videoLink = dados.videoLink();
            this.updatedAt = LocalDate.now();
        } if (!(dados.photoLink() == null)) {
            this.photoLink = dados.photoLink();
            this.updatedAt = LocalDate.now();
        }



    }

    public void increaseLikes() {
        likes++;
    }
    public void decreaseLikes() {
        likes--;
    }


    public void delete() {
        this.deleted = true;
    }

    public void updateVisibility() {
        this.postIsPrivate = !(this.postIsPrivate);
    }
}
