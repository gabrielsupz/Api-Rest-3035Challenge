package com.teach.challenge.controllers;

import com.teach.challenge.domain.models.post.DetailPostDataDTO;
import com.teach.challenge.domain.models.post.DataPostDataDTO;
import com.teach.challenge.domain.models.post.GetAuthorDTO;
import com.teach.challenge.domain.models.post.UpdatePostDTO;
import com.teach.challenge.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;


    @PostMapping
    @Transactional
    public ResponseEntity<DetailPostDataDTO>  publishPost(HttpServletRequest request, @RequestBody @Valid DataPostDataDTO dados, UriComponentsBuilder uriBuilder) {
        return postService.publishPost(request, dados, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable Long id) {


        return postService.detail(id);
    }


    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DetailPostDataDTO> update(HttpServletRequest request, @RequestBody @Valid UpdatePostDTO dados) {

        return postService.update( request,dados);

    }


    @PutMapping("/likes")
    @Transactional
    public ResponseEntity<DetailPostDataDTO> increaseLikes(HttpServletRequest request,@RequestBody @Valid UpdatePostDTO data){

        return postService.increaseLikes(request, data);

    }

    @PutMapping("/likes/remove")
    @Transactional
    public ResponseEntity<DetailPostDataDTO> removeLikes(HttpServletRequest request,@RequestBody @Valid UpdatePostDTO data){

        return postService.removeLikes(request, data);

    }



    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(HttpServletRequest request,@PathVariable Long id){


        return postService.delete(request,id);


    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateVisibility(HttpServletRequest request,@PathVariable Long id){


        return postService.updateVisibility(request,id);


    }




    @GetMapping("/user/{id}")
    @Transactional
    public ResponseEntity<Page<DetailPostDataDTO>> listPostUserById(@PathVariable Long id, @PageableDefault(size = 6) Pageable pageble){


        return postService.listPostUserById(id, pageble);

    }



    @GetMapping("/friends")
    @Transactional
    public ResponseEntity<Page<DetailPostDataDTO>> listFriendsPostsByUser(HttpServletRequest request, @PageableDefault(size = 5) Pageable pageble){


        return postService.listFriendsPostsByUser(request,pageble);

    }




    @GetMapping
    public ResponseEntity<Page<DetailPostDataDTO>> listPostsByUser(HttpServletRequest request, @PageableDefault(size = 5) Pageable pageble){


        return postService.listPostByUser(request,pageble);

    }


}