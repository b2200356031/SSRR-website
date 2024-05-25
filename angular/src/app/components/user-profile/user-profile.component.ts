import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { UserProfile } from 'src/app/userprofile';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit{

  userProfile: UserProfile;
  userProfileForm: FormGroup

  constructor(private userService: UserService, private fb: FormBuilder, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    // Load the user's data when the component initializes
    this.userService.getUserProfile().subscribe(profile => {
      this.userProfile = profile;
    });

    this.userProfileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      nick: ['', Validators.required],
      // validator error fix
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]],
      confirmPassword: ['', [Validators.required, this.passwordValidator]],
    })
  }

  private passwordValidator(control: FormControl):{ [s: string]: boolean } | null {
    const value: string = control.value || '';
    const valid = value.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/);
    return valid ? null :{'passwordInvalid': true};
  }

  onSubmit(): void {
    // Handle form submissions here

    if(this.userProfileForm.valid){
      this.userService.updateUserProfile(this.userProfileForm.value).subscribe((response) => {
        
        alert("User credentials are upated successfully. Please login again.");
        
        this.authService.logout();
        
        this.router.navigate(['/login']);
      })
    }
  }

  deleteProfile() {

    this.userService.deleteUser();
    alert("User deleted sucessfully");
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
