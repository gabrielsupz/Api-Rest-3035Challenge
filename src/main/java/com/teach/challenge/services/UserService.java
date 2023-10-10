package com.teach.challenge.services;

import com.teach.challenge.domain.models.user.*;
import com.teach.challenge.domain.repositorys.UserRepository;
import com.teach.challenge.infra.security.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<DetailUserDataDTO> register(@RequestBody @Valid RegisterUserDataDTO dados, UriComponentsBuilder uriBuilder) {

        User user = new User(dados);
        if (userRepository.existsByUserName(user.getUserName()) || userRepository.existsByEmail(user.getEmail()) || userRepository.existsByPhone(user.getPhone())) {

            return ResponseEntity.badRequest().build();
        }

        userRepository.save(user);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();


        return ResponseEntity.created(uri).body(new DetailUserDataDTO(user));


    }


    public ResponseEntity<Object> detail(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            return ResponseEntity.ok(new DetailUserDataDTO(u));
        }

        return ResponseEntity.notFound().build();
    }






//OBS: O id do user a sofrer o update será pego do token de autenticação
    public ResponseEntity<DetailUserDataDTO> update(HttpServletRequest request, @RequestBody @Valid UpdateUserDataDTO dados) {
        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User u = user.get();
            u.updateData(dados);

            return ResponseEntity.ok(new DetailUserDataDTO(u));
        }
        return ResponseEntity.notFound().build();


    }

    public ResponseEntity<?> addFriend(HttpServletRequest request, FriendUserDataDTO dados) {

        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);
        Optional<User>  friend = userRepository.findById(dados.id());

        if (user.isPresent() && friend.isPresent()) {
            User u = user.get();
            User f = friend.get();
            u.addFriend(f);
            f.addFriend(u);
            return ResponseEntity.ok().build();
        }
            return ResponseEntity.badRequest().build();
    }


    public ResponseEntity<?> removeFriend(HttpServletRequest request, FriendUserDataDTO dados) {

        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);
        Optional<User>  friend = userRepository.findById(dados.id());

        if (user.isPresent() && friend.isPresent()) {
            User u = user.get();
            User f = friend.get();
            u.removeFriend(f);
            f.removeFriend(u);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    public ResponseEntity<Page<DetailUserDataDTO>> getFriends(HttpServletRequest request, Pageable pageable) {
        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User u = user.get();



            Page<DetailUserDataDTO> friendPage = userRepository.findActiveFriendsByUserId(u.getId(),pageable ).map(friend -> new DetailUserDataDTO(friend));

            return ResponseEntity.ok(friendPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Page<DetailUserDataDTO>> listUsers(Pageable pageble) {

        Page<DetailUserDataDTO> page = userRepository.findAllByDeletedFalse(pageble).map(u -> new DetailUserDataDTO(u));

        return ResponseEntity.ok(page);
    }
    

//OBS: users deletados não poderão efetuar o login
    public ResponseEntity<?> delete(HttpServletRequest request) {
        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.delete();
            u.isAccountNonLocked();
        }

        return ResponseEntity.noContent().build();

    }


    public ResponseEntity<Object> getAuthUser(HttpServletRequest request) {
        var tokenJWT = tokenService.retriveToken(request);
        Long id = tokenService.getID(tokenJWT);
        Optional<User> user = userRepository.findById(id);


        if (user.isPresent()) {
            User u = user.get();
            return ResponseEntity.ok(new DetailUserDataDTO(u));
        }

        return ResponseEntity.notFound().build();



    }
}
