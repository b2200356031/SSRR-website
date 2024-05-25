import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommunityRoutingModule } from './community-routing.module';
import { PostCardComponent } from './components/post/post-card/post-card.component';
import { ForumComponent } from './pages/forum/forum.component';
import { AddPostComponent } from './components/post/add-post/add-post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { PostDetailsComponent } from './components/post/post-details/post-details.component'; 
import {HttpClientModule} from '@angular/common/http';
import { AddCommentComponent } from './components/post/add-comment/add-comment.component';
import { CommentComponent } from './components/post/comment/comment.component';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
@NgModule({
  declarations: [
  
    PostCardComponent,
       ForumComponent,
       AddPostComponent,
       PostDetailsComponent,
       AddCommentComponent,
       CommentComponent,
  ],
  imports: [
    CommonModule,
    CommunityRoutingModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    HttpClientModule,
    MatIconModule,
    FormsModule

  ]
})
export class CommunityModule { }
