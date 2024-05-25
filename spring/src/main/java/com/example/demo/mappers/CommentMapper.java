package com.example.demo.mappers;


import com.example.demo.DTO.CommentDto;
import com.example.demo.Entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto map(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCreationDate(comment.getCreationDate());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setLikesCount(comment.getLikesCount());
        commentDto.setUsersDto(UsersMapper.map(comment.getUser()));
        commentDto.setCreationDate(comment.getCreationDate());
        return commentDto;
    }
    public Comment map(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setCreationDate(commentDto.getCreationDate());
        return comment;
    }
}
