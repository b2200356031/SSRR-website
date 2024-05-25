import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
showResetPasswordForm: any;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  forgotPasswordForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, this.passwordValidator]],
    confirmPassword: ['', [Validators.required, this.passwordValidator]],
  }, { validator: this.passwordMatchValidator 
    
  });

  ngOnInit(): void {
    
  }

  onSubmit(): void {
    if (this.forgotPasswordForm.valid) {

      const email = this.forgotPasswordForm.get('email').value;
      const password = this.forgotPasswordForm.get('password').value;

      this.userService.updatePassword(email, password).subscribe((response) => {
        
        if (response) 
        {
          alert("Password successfully changed. Please login.");
          this.router.navigate(['/login']);
        } 
        else 
        {
          alert('User does not exist!');
        }
      })
    }
  }

  private passwordValidator(control: FormControl):{ [s: string]: boolean } | null {
    const value: string = control.value || '';
    const valid = value.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/);
    return valid ? null :{'passwordInvalid': true};
  }

  private passwordMatchValidator(fg: FormGroup) {
    const password = fg.get('password')?.value;
    const confirmPassword = fg.get('confirmPassword')?.value;
    if (password != confirmPassword) {
      fg.get("confirmPassword")?.setErrors({ passwordMismatch: true });
    } else {
      fg.get('confirmPassword')?.setErrors(null);
    }
  }


}