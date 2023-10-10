package com.teach.challenge.controllers;



import com.teach.challenge.domain.models.user.*;
import com.teach.challenge.domain.repositorys.UserRepository;
import com.teach.challenge.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RequestMapping("/usuarios")
@RestController
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthController authController;



    @PostMapping
    public ResponseEntity<DetailUserDataDTO> cadastrar(@RequestBody @Valid RegisterUserDataDTO dados, UriComponentsBuilder uriBuilder) {

        return userService.register(dados, uriBuilder);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable Long id) {


        return userService.detail(id);
    }




    @GetMapping("/authuser")
    public ResponseEntity<Object> getAuthUser(HttpServletRequest request) {


        return userService.getAuthUser(request);
    }




        @PutMapping("/update")
    @Transactional
    public ResponseEntity<DetailUserDataDTO> update(HttpServletRequest request, @RequestBody @Valid UpdateUserDataDTO dados) {

        return userService.update(request, dados);


    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<?> delete(HttpServletRequest request) {


        return userService.delete(request);


    }

    @PostMapping("/friends")
    @Transactional
    public ResponseEntity<?> addFriend(HttpServletRequest request, @RequestBody @Valid FriendUserDataDTO dados) {


        return userService.addFriend(request,dados);


    }


    @PutMapping("/friends")
    @Transactional
    public ResponseEntity<?> removeFriend(HttpServletRequest request, @RequestBody @Valid FriendUserDataDTO dados) {


        return userService.removeFriend(request,dados);


    }

    @GetMapping("/friends")
    @Transactional
    public ResponseEntity<?> getFriends(HttpServletRequest request,@PageableDefault(size = 4) Pageable pageble) {


        return userService.getFriends(request,pageble);


    }



    @GetMapping
    public ResponseEntity<Page<DetailUserDataDTO>> listUser(@PageableDefault(size = 5) Pageable pageble) {


        return userService.listUsers(pageble);

    }






}
