import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  signupForm!: FormGroup;
  submitClicked = false;
  message: any;

  constructor(
    private service: AuthService,
    private fb: FormBuilder, private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.signupForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      // validator error fix
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]],
      confirmPassword: ['', [Validators.required, this.passwordValidator]],
      role: ['', Validators.required]

    }, { validator: this.passwordMatchValidator })

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

  register() {
    this.submitClicked = true;
    console.log(this.signupForm.value);
    this.service.register(this.signupForm.value).subscribe((response) => {
      this.succesMsg();
    })
  }

  succesMsg(){
    alert("*** User Registered Successfully ***")
    this.router.navigate(['login']);
  }

}
