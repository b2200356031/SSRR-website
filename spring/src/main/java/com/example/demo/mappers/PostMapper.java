package com.example.demo.mappers;


import com.example.demo.DTO.PostDto;
import com.example.demo.Entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto map(Post post){
        PostDto postDto=new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setLikesCount(post.getLikesCount());
        postDto.setViewsCount(post.getViewsCount());
        postDto.setCommentsCount(post.getComments().size());
        postDto.setUsersDto(UsersMapper.map(post.getUser()));
        postDto.setCreationDate(post.getCreationDate());
        return postDto;
    }
    public Post map(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setLikesCount(postDto.getLikesCount());
        post.setViewsCount(postDto.getViewsCount());
        post.setCommentsCount(postDto.getCommentsCount());
        return post;
    }
}
