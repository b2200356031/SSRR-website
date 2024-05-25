import { Component, Input } from '@angular/core';
import { Post } from '../../../models/Post';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent {
  constructor(private router: Router){}

  @Input() post!: Post;

  openPostDetails(): void {
    this.router.navigate(['/forum/post/details', this.post.id], { state: {  post: this.post  } });
  }
}
