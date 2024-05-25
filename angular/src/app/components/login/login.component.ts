import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm?: any;

  constructor(private fb: FormBuilder, private authService : AuthService, private router: Router) {}

  private authSecretKey = 'authToken';

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.required]
    });
  }
  
  onSubmit(): void {
    if (this.loginForm.valid) {
       this.authService.login(this.loginForm.value).subscribe(response => {
        if (response.token) {
          this.authService.isAuthenticated = true;
          const token = response.token.trim();
          localStorage.setItem(this.authSecretKey, response.token);
          this.router.navigate(['/home'])
        }
        else {
          alert("provided credentials are not correct!");        
        }
      });
    }
  }
}
