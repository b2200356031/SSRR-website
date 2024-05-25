import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '';
  
  constructor(public authService: AuthService, private router : Router) {}

  logout(): void {
    if(!this.authService.isLoggedIn()){
      alert("You need to be logged in to log out!")
      this.router.navigate(['/login']);
    }
    else{
      this.authService.logout();
      this.router.navigate(['/home']);
    }
  }
}
