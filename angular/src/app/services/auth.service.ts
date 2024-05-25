import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';

const BASE_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  public isAuthenticated = false;

  private authSecretKey = 'authToken';

  constructor(private http: HttpClient) {
    console.log();
    
    if(localStorage.getItem(this.authSecretKey)){
      this.isAuthenticated = true;
    }
    //this.isAuthenticated = !!localStorage.getItem(this.authSecretKey);
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(BASE_URL + "auth/login", loginRequest);
  }

  getToken(): string | null {
    return localStorage.getItem(this.authSecretKey);
  }

  register(signupRequest: any): Observable<any> {
    return this.http.post(BASE_URL + "auth/register", signupRequest);
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }

  isLoggedIn(): boolean {
    if (this.isAuthenticated) {
      return true;
    }
    else {
      return false;
    }
  }

  logout(): void {
    localStorage.removeItem(this.authSecretKey);
    this.isAuthenticated = false;
    
  }
}
