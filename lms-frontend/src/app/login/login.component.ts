import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_service/user-auth.service';
import { UsersService } from '../_service/users.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    standalone: false
})
export class LoginComponent implements OnInit {
  errorMessage: string = ''; // ✅ Add error message variable

  constructor(
    private userService: UsersService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit() {}

  login(loginForm: NgForm) {
    this.errorMessage = ''; // ✅ Clear previous errors

    this.userService.login(loginForm.value).subscribe(
      (response: any) => {
        const roles = response.user.role.map((r: any) => r.roleName);
        this.userAuthService.saveUserData(
          response.jwtToken,
          roles,
          response.user.userId,
          response.user.name
        );

        if (roles.includes('Admin')) {
          this.router.navigate(['/books']);
        } else {
          this.router.navigate(['/borrow-book']);
        }
      },
      (error) => {
        console.error("Login Failed", error);

        // ✅ Check specific error cases
        if (error.status === 401) {
          this.errorMessage = "Invalid username or password. Please try again.";
        } else if (error.status === 404) {
          this.errorMessage = "User does not exist.";
        } else {
          this.errorMessage = "Login failed. Please check your credentials.";
        }
      }
    );
  }
}
