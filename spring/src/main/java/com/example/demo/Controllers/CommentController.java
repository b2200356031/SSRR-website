package com.example.demo.Controllers;


import com.example.demo.DTO.CommentDto;
import com.example.demo.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(commentDto));
    }
    @PutMapping("")
    public ResponseEntity<CommentDto> updatePost(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(commentDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/comments/by/post/{postId}")
    public ResponseEntity<List<CommentDto>> getUser(@PathVariable  Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
    @PutMapping("/{action}/{id}")
    public CommentDto likeOrDislike(@PathVariable String action, @PathVariable Long id){
        return commentService.likeOrDislike(action,id);
    }
}
