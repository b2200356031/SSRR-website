import { UserService } from 'src/app/services/user.service';
import { CommentService } from '../../../services/comment.service';
import { Comment } from './../../../models/Comment';
import { Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { UserProfile } from 'src/app/userprofile';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  @Input() comment!: Comment;
  @Output() commentDeleted = new EventEmitter<number>();

  edit:boolean=false;
  canEdit:boolean=false;
  user:UserProfile
  showButtons:boolean=false;
   
  constructor(private commentService:CommentService,private userService:UserService){
  }
  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(profile => {
      this.user = profile;
      if(this.user) this.canEdit=this.user.id===this.comment?.usersDto.id

    });
  }

  likeOrDislikeComment(action:string){
    if(this.comment.id) this.commentService.likeOrDislikeComment(action,this.comment.id).subscribe((comment)=>{
        this.comment=comment
    })
  }
  deleteComment(){
    if(this.comment.id) this.commentService.deleteComment(this.comment.id).subscribe((res)=>  this.commentDeleted.emit(this.comment.id))
  }
  updateComment(){
    this.commentService.updateComment(this.comment).subscribe((res)=>this.edit=false)
  }
  isEditing(){
    this.edit=true;
  }

  setShowButtons(value:boolean){
    this.showButtons=value;
  }
 
}
