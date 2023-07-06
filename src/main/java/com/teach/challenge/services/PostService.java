package com.teach.challenge.services;

import com.teach.challenge.domain.models.post.DetailPostDataDTO;
import com.teach.challenge.domain.models.post.DataPostDataDTO;
import com.teach.challenge.domain.models.post.Post;
import com.teach.challenge.domain.models.post.UpdatePostDTO;
import com.teach.challenge.domain.models.user.User;
import com.teach.challenge.domain.repositorys.PostRepository;
import com.teach.challenge.domain.repositorys.UserRepository;
import com.teach.challenge.infra.security.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    //Vinculando o post ao user "logado" através do id que vem com o token
    public ResponseEntity<DetailPostDataDTO> publishPost(HttpServletRequest request, @RequestBody @Valid DataPostDataDTO dados, UriComponentsBuilder uriBuilder) {


        User user = getUserLogged(request);
        Post post = new Post(dados, user);
        postRepository.save(post);

        URI uri = uriBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri();


        return ResponseEntity.created(uri).body(new DetailPostDataDTO(post));
    }

    private User getUserLogged(HttpServletRequest request) {
        String tokenJWT = tokenService.retriveToken(request);
        Long idUser = tokenService.getID(tokenJWT);

      return  userRepository.getById(idUser);
    }

    public ResponseEntity<Object> detail(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            return ResponseEntity.ok(new DetailPostDataDTO(p));
        }

        return ResponseEntity.notFound().build();
    }

    // O  user só pode realizar um update nos seus próprios posts"
    public ResponseEntity<DetailPostDataDTO> update(HttpServletRequest request, UpdatePostDTO data) {
        User user = getUserLogged(request);

        Optional<Post> post = postRepository.findById(data.id());


        if (post.isPresent()) {
            if (post.get().getPostCreator() == user) {
                Post p = post.get();
                p.updateData(data);

                return ResponseEntity.ok(new DetailPostDataDTO(p));
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
// O  user só pode deletar um de seus próprios posts"

    public ResponseEntity<?> delete(HttpServletRequest request, Long postId) {

        User user = getUserLogged(request);


        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            if (post.get().getPostCreator() == user) {
                Post p = post.get();
                p.delete();
                return ResponseEntity.noContent().build();

            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<?> updateVisibility(HttpServletRequest request, Long postId) {
        User user = getUserLogged(request);
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            if (post.get().getPostCreator() == user) {
                Post p = post.get();
                p.updateVisibility();

                return ResponseEntity.noContent().build();
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    //Listando todos os post do próprio user
    public ResponseEntity<Page<DetailPostDataDTO>> listPostByUser(HttpServletRequest request, Pageable pageble) {
        User user = getUserLogged(request);
        Page<DetailPostDataDTO> page = postRepository.findAllUndeletedUserPosts(user.getId(), pageble).map(p -> new DetailPostDataDTO(p));
        return ResponseEntity.ok(page);

    }
}
