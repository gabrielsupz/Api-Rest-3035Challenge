package com.teach.challenge.services;

import com.teach.challenge.domain.models.user.*;
import com.teach.challenge.domain.repositorys.UserRepository;
import com.teach.challenge.infra.security.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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


    public ResponseEntity<Page<DetailUserDataDTO>> listUsers(Pageable pageble) {

        Page<DetailUserDataDTO> page = userRepository.findAllByDeletedFalse(pageble).map(u -> new DetailUserDataDTO(u));

        return ResponseEntity.ok(page);
    }


}
