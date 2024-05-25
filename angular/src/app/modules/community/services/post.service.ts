import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  readonly API_URL = "http://localhost:8080/";
  readonly ADD_POST_URL = this.API_URL + "api/post"
  readonly GET_ALL_POST_URL = this.API_URL + "api/post/allPosts"

  private authSecretKey = 'authToken';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const authToken = localStorage.getItem(this.authSecretKey);
    console.log(authToken);

    if (authToken && authToken.trim() !== '') {
      return new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authToken.trim()}`
      });
    } else {
      throw new Error('Auth token is not available');
    }
  }
  addPost(post: Post): Observable<Post> {
    const headers = this.getHeaders();

    return this.http.post<Post>(this.ADD_POST_URL, post,{headers})
  }
  updatePost(post: Post): Observable<Post> {
    const headers = this.getHeaders();
    return this.http.put<Post>(this.ADD_POST_URL, post,{headers})
  }
  deletePost(postId: number): Observable<void> {
    const headers = this.getHeaders();
    return this.http.delete<void>(this.ADD_POST_URL+"/"+postId,{headers})
  }
  getPosts(): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get<any>(this.GET_ALL_POST_URL,{headers})
  }
  likeOrDislikeComment(action:string,id:number){
    const headers = this.getHeaders();
    return this.http.put<Post>(this.ADD_POST_URL+"/"+action+"/"+id, {},{headers})
  }
}
