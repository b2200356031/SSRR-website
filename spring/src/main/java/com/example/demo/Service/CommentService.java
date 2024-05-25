package com.example.demo.Service;


import com.example.demo.DTO.CommentDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.UserLike;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.CommentRepository;
import com.example.demo.UserRepo.LikeRepo;
import com.example.demo.UserRepo.PostRepository;
import com.example.demo.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UtilService utilService;
    private final LikeRepo likeRepo;
    public CommentDto addComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("post not found"));
        Users user = utilService.getAuthenticatedUser();
        Comment comment = commentMapper.map(commentDto);
        post.getComments().add(comment);
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreationDate(LocalDateTime.now());
        comment = commentRepository.save(comment);
        user.getComments().add(comment);
        return commentMapper.map(comment);
    }

    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new RuntimeException("comment not found"));
        comment.setText(commentDto.getText());
        return commentMapper.map(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not foundd"));
        return  post.getComments().stream()
                .sorted(Comparator.comparing(Comment::getId))
                .map(commentMapper::map)
                .toList();
    }

    public CommentDto likeOrDislike(String action, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        Users user = utilService.getAuthenticatedUser();

        Optional<UserLike> existingLike = likeRepo.findByUserAndComment(user, comment);

        switch (action) {
            case "like":
                if (existingLike.isPresent()) {
                    return commentMapper.map(comment);
                }
                UserLike userLike = new UserLike();
                userLike.setUser(user);
                userLike.setComment(comment);
                likeRepo.save(userLike);
                if(comment.getUserLikes()==null){
                    comment.setUserLikes(new HashSet<>());
                }
                if(user.getUserLikes()==null){
                    user.setUserLikes(new HashSet<>());
                }
                comment.getUserLikes().add(userLike);
                user.getUserLikes().add(userLike);
                comment.setLikesCount(comment.getLikesCount() != null ? comment.getLikesCount() + 1 : 1);
                commentRepository.save(comment);
                return commentMapper.map(comment);

            case "dislike":
                if (existingLike.isPresent()) {
                    likeRepo.delete(existingLike.get());
                    comment.getUserLikes().remove(existingLike.get());
                    user.getUserLikes().remove(existingLike.get());
                    comment.setLikesCount(comment.getLikesCount() != null && comment.getLikesCount() > 0 ? comment.getLikesCount() - 1 : 0);
                    commentRepository.save(comment);
                }
                return commentMapper.map(comment);

            default:
                return commentMapper.map(comment);
        }
    }
}
