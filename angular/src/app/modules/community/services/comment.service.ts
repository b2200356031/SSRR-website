import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../../community/models/Comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  readonly API_URL = "http://localhost:8080/";
  readonly ADD_COMMENT_URL = this.API_URL + "api/comment"
  readonly GET_ALL_COMMENTS_BY_POST_URL = this.API_URL + "api/comment/comments/by/post"

  constructor(private http: HttpClient) { }
  private authSecretKey = 'authToken';

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
  addComment(comment: Comment): Observable<Comment> {
    const headers = this.getHeaders();

    return this.http.post<Comment>(this.ADD_COMMENT_URL, comment,{headers})
  }

  updateComment(comment: Comment): Observable<Comment> {
    const headers = this.getHeaders();
    return this.http.put<Comment>(this.ADD_COMMENT_URL, comment,{headers})
  }
  deleteComment(commentId: number): Observable<void> {
    const headers = this.getHeaders();
    return this.http.delete<void>(this.ADD_COMMENT_URL+"/"+commentId,{headers})
  }
  getCommentsByPost(postId:number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.get<any>(this.GET_ALL_COMMENTS_BY_POST_URL+"/"+postId,{headers})
  }
  likeOrDislikeComment(action:string,id:number){
    const headers = this.getHeaders();
    return this.http.put<Comment>(this.ADD_COMMENT_URL+"/"+action+"/"+id, {},{headers})
  }
}
