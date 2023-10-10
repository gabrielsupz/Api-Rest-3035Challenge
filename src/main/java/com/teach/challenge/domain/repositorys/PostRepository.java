package com.teach.challenge.domain.repositorys;

import com.teach.challenge.domain.models.post.DetailPostDataDTO;
import com.teach.challenge.domain.models.post.Post;
import com.teach.challenge.domain.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

  // Page<Post> findAllByPostCreator(User creator, Pageable page);


    @Query(value = "select * from post where post_creator_id = ?1 and deleted = 0", nativeQuery = true)
    Page<Post> findAllUndeletedUserPosts(Long id , Pageable page);


    @Query("SELECT p FROM Post p JOIN p.postCreator u JOIN u.friends f WHERE f.id = ?1 AND p.deleted = false")
    Page<Post> findAllActivePostsByFriendUserId(Long userId, Pageable pageable);

    Page<Post> findAllByPostCreatorAndDeletedFalse(User user, Pageable pageable);


}
