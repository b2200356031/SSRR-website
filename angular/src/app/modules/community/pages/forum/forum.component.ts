import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddPostComponent } from '../../components/post/add-post/add-post.component';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post.service';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {
  postsSubscription: Subscription | undefined
  posts: Post[] = []

  constructor(public dialog: MatDialog,private postService:PostService) { }
  ngOnInit(): void {
    this.getPosts()
  }

  getPosts(): void {
    this.postsSubscription = this.postService.getPosts().subscribe((posts) => {
      this.posts = posts;
    });
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(AddPostComponent, {
      width: '500px',
      height: '500px',
      data: { name: 'Angular' }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed. Received data:', result);
      this.getPosts()
    });
  }

  ngOnDestroy(): void {
    this.postsSubscription?.unsubscribe()
  }
}
