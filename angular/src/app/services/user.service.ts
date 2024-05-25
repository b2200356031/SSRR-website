import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { UserProfile } from '../userprofile';
import { Review } from '../review';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  // TODO: change bearer token to this!!
  private authSecretKey = 'authToken';


  private getToken(): string {
    const token = localStorage.getItem(this.authSecretKey);
  
    if (!token) {
      throw new Error('No token found');
    }
  
    return token;
  }
  
  getUserProfile(): Observable<UserProfile> {

    const token = this.getToken();

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    return this.http.get<UserProfile>('http://localhost:8080/user', { headers: headers });
  }


  updateUserProfile(updateForm: any): Observable<UserProfile>{
    
    const token = this.getToken();

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    return this.http.put<UserProfile>('http://localhost:8080/user/update', updateForm, { headers: headers });
  }

  setInterestedCategory( categoryId: number): Observable<number> {
    const token = this.getToken();

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });
    return this.http.put<number>(`http://localhost:8080/user/interestedCategory`,  categoryId ,{ headers: headers });
  }

  
  deleteInterestedCategory( categoryId: number): Observable<number> {
    const token = this.getToken();

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });
    return this.http.delete<number>(`http://localhost:8080/user/deleteInterestedCategory/${categoryId}`,{ headers: headers });
  }

  updatePassword(email: string, password: string): Observable<UserProfile>
  {
    const payload = { email, password };
    return this.http.put<UserProfile>('http://localhost:8080/forgotpassword', payload).pipe(
      catchError(error => {
        if(error.status === 404){
          alert("User does not exist!")
        }
        return throwError(error);
      })
    );
  }

  

  deleteUser() : any{
    const token = this.getToken();

    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });

    return this.http.delete<any>('http://localhost:8080/deleteuser', { headers: headers }).subscribe((response => {
      console.log(response);
    }))
  }
  
}
