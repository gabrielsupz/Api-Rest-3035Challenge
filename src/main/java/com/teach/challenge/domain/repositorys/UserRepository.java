package com.teach.challenge.domain.repositorys;


import com.teach.challenge.domain.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {


    Boolean existsByUserName(String userName);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);


    Page<User> findAllByDeletedFalse(Pageable page);

    UserDetails findByUserName(String username);

    UserDetails findByEmail(String username);

    @Query("SELECT f FROM User u JOIN u.friends f WHERE u.id = :userId AND f.deleted = false")
    Page<User> findActiveFriendsByUserId(Long userId, Pageable pageable);
}
