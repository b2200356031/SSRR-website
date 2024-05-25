import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PostService } from '../../../services/post.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent {
  postForm!: FormGroup;
  addPostSubscription: Subscription | undefined

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<AddPostComponent> ,
    private postService:PostService
  ) {}

  ngOnInit(): void {
    this.postForm = this.formBuilder.group({
      title: ['', Validators.required],
      content: ['', [Validators.required, Validators.minLength(10)]]
    });

  }


  onSubmit() {
    if (this.postForm.valid) {
      console.log(this.postForm.value);
      this.addPostSubscription= this.postService.addPost(this.postForm.value).subscribe((post)=>console.log(post))
      this.dialogRef.close();
    }
  }
  
  ngOnDestroy(): void {
    this.addPostSubscription?.unsubscribe()
  }
  onCancel() {
    this.dialogRef.close(); 
  }
}
