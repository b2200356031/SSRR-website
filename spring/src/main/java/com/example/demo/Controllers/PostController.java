package com.example.demo.Controllers;


import com.example.demo.DTO.PostDto;
import com.example.demo.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.addPost(postDto));
    }
    @PutMapping("")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/allPosts")
    public ResponseEntity<List<PostDto>> getUser() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @PutMapping("/{action}/{id}")
    public PostDto likeOrDislike(@PathVariable String action, @PathVariable Long id){
        return postService.likeOrDislike(action,id);
    }
}
