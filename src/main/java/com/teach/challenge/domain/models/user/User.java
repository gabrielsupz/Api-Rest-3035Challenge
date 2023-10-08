package com.teach.challenge.domain.models.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.teach.challenge.domain.models.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;
    private String password;
    private String name;

    private Boolean deleted;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String email;
    private String phone;
    private String description;
    private String profileLink;
    @OneToMany(mappedBy = "postCreator", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList();


    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();

    public void addFriend(User friend) {
        friends.add(friend);

        
    }

    public List<User> getFriends() {
        return friends;
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public User(RegisterUserDataDTO d) {
        this.password = new BCryptPasswordEncoder().encode(d.password());


        this.deleted = false;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.name = d.name();
        this.phone = d.phone();
        this.email = d.email();
        this.userName = d.userName();

        this.profileLink = d.profileLink();
        this.description = d.description();

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Users deletados não poderão mais realizar o login
    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }


    public void updateData(UpdateUserDataDTO dados) {

        if (!(dados.userName() == null)) {
            this.userName = dados.userName();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.name() == null)) {
            this.name = dados.name();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.password() == null)) {
            this.password = dados.password();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.email() == null)) {
            this.email = dados.email();
            this.updatedAt = LocalDate.now();
        }

        if (!(dados.phone() == null)) {
            this.phone = dados.phone();
            this.updatedAt = LocalDate.now();
        }
        if (!(dados.profileLink() == null)) {
            this.profileLink = dados.profileLink();
            this.updatedAt = LocalDate.now();
        }

        if (!(dados.description() == null)) {
            this.description = dados.description();
            this.updatedAt = LocalDate.now();
        }


    }

    public void delete() {

        this.deleted = true;


    }
}
