import { Component, EventEmitter, Output } from '@angular/core';
import { Comment } from './../../../models/Comment';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent {
  @Output() commentAdded = new EventEmitter<Comment>();
  showButtons:boolean=false;
  comment!:Comment
  text!:string

  onAddComment() {
    this.comment={text:this.text}
    this.commentAdded.emit(this.comment);
    this.showButtons=false;
    this.text=""
  }
  setShowButtons(value:boolean){
    this.showButtons=value;
  }

}
