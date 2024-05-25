import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review } from '../review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
    private apiUrl = 'http://localhost:8080';
    private authSecretKey = 'authToken';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const authToken = localStorage.getItem(this.authSecretKey);

    if (authToken && authToken.trim() !== '') {
      return new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authToken.trim()}`
      });
    } else {
      throw new Error('Auth token is not available');
    }
  }

  getReviews(productId: number): Observable<Review[]> {
    const headers = this.getHeaders();
    return this.http.get<Review[]>(`${this.apiUrl}/product/${productId}`, {headers});
  }

  addReview(productId: number, review: any): Observable<any> {
    const url = `${this.apiUrl}/product/${productId}`;
    const headers = this.getHeaders();

    return this.http.post<any>(url, review, {headers}).pipe(
      catchError(this.handleError)
    );
  }

  updateReview(review: Review): Observable<Review> {
    const headers = this.getHeaders();
    return this.http.put<Review>(`${this.apiUrl}/product/update`, review, {headers});
  }

  deleteReview(reviewId: number): Observable<void> {
    const headers = this.getHeaders();

    return this.http.delete<void>(`${this.apiUrl}/product/delete/${reviewId}`, { headers });
  }

  addPoints(review:Review):Observable<Review>{
    const headers = this.getHeaders();

    return this.http.put<Review>('http://localhost:8080/addPoints', review,{ headers: headers } )
  }



  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(error.message || 'Server error');
  }
}