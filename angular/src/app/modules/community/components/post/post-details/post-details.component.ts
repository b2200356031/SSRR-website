import { PostService } from './../../../services/post.service';
import { Subscription } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../../models/Post';
import { Router } from '@angular/router';
import { CommentService } from '../../../services/comment.service';
import { Comment } from '../../../models/Comment';
import { UserService } from 'src/app/services/user.service';
import { UserProfile } from 'src/app/userprofile';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post!: Post;
  comments:Comment[]=[];
  edit:boolean=false;
  canEdit:boolean=false;
  showButtons:boolean=false;
  user:UserProfile
  constructor(private router: Router,private commentService:CommentService,private postService:PostService,private userService:UserService){
    const navigation = this.router.getCurrentNavigation();
    this.post = navigation?.extras.state?.['post'] as Post;
    
  }
  
  ngOnInit(): void {
    if(this.post) this.commentService.getCommentsByPost(this.post.id).subscribe((comments)=>{
      this.comments=comments
    })
    this.userService.getUserProfile().subscribe(profile => {
      this.user = profile;
      if(this.user) this.canEdit=this.user.id===this.post.usersDto.id
    });
  }
  addComment(comment:Comment){
    if(this.post){
      comment.postId=this.post.id
      this.commentService.addComment(comment).subscribe((comment)=>{
        this.comments.push(comment)
      })
    }
  }
  likeOrDislikeComment(action:string){
    if(this.post?.id) this.postService.likeOrDislikeComment(action,this.post.id).subscribe((post)=>{
        this.post=post
    })
  }
  deletePost(){
    if(this.post?.id) this.postService.deletePost(this.post.id).subscribe((res)=>this.router.navigate(['/forum']))
  }
  updatePost(){
    this.postService.updatePost(this.post).subscribe((res)=>{
      this.edit=false;
    }
    )
  }
  isEditing(){
    this.edit=true;
  }

  setShowButtons(value:boolean){
    this.showButtons=value;
  }
  handleCommentDeleted(commentId: number) {
    this.comments = this.comments.filter(comment => comment.id !== commentId);
  }
}
