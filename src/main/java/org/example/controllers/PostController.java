package org.example.controllers;

import org.example.dto.post.PostCreateDTO;
import org.example.dto.post.PostEditDTO;
import org.example.dto.post.PostItemDTO;
import org.example.mapper.PostMapper;
import org.example.entities.PostEntity;
import org.example.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<List<PostItemDTO>> getAllPosts() {
        List<PostItemDTO> posts = postRepository.findAll()
                .stream()
                .map(postMapper::postItemDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostItemDTO> createPost(@RequestBody PostCreateDTO dto) {
        try {
            PostEntity entity = postMapper.createDtoEntity(dto);
            postRepository.save(entity);
            return new ResponseEntity<>(postMapper.postItemDto(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostItemDTO> getPostById(@PathVariable int postId) {
        PostEntity entity = postRepository.findById(postId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postMapper.postItemDto(entity), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostItemDTO> updatePost(@PathVariable int postId, @RequestBody PostEditDTO dto) {
        PostEntity old = postRepository.findById(postId).orElse(null);
        if(old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PostEntity entity = postMapper.editDtoEntity(dto);
        entity.setId(postId);
        postRepository.save(entity);
        return new ResponseEntity<>(postMapper.postItemDto(entity), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        PostEntity entity = postRepository.findById(postId).orElse(null);
        if(entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            postRepository.delete(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
