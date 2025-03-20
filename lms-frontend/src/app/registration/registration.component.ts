import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../_service/users.service';
import { UserAuthService } from '../_service/user-auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.css'],
    standalone: false
})
export class RegistrationComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage: string = '';

  selectedGenres: string[] = [];
  selectedAuthors: string[] = [];
  selectedPreferences: string[] = [];
  selectedFrequency: string = '';

  genres = ['Fantasy', 'Mystery', 'Sci-Fi', 'Romance', 'Historical Fiction'];
  authors = ['J.K. Rowling', 'Agatha Christie', 'George Orwell', 'Stephen King', 'Jane Austen'];
  preferences = ['Suspense', 'Adventure', 'Character Development', 'Thought-provoking', 'Humor'];
  readingFrequencies = ['One', 'Two', 'Three', 'More than three'];

  constructor(
    private fb: FormBuilder,
    private userService: UsersService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]], // Ensure email format
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  toggleSelection(option: string, type: string) {
    switch (type) {
      case 'genre':
        this.toggleArraySelection(option, this.selectedGenres);
        break;
      case 'author':
        this.toggleArraySelection(option, this.selectedAuthors);
        break;
      case 'preference':
        this.toggleArraySelection(option, this.selectedPreferences);
        break;
    }
  }

  toggleArraySelection(option: string, array: string[]) {
    const index = array.indexOf(option);
    if (index > -1) {
      array.splice(index, 1);
    } else {
      array.push(option);
    }
  }

  selectFrequency(option: string) {
    this.selectedFrequency = option;
  }

  registerUser() {
    if (this.registerForm.invalid) {
      return;
    }

    this.userService.register(this.registerForm.value).subscribe(
      (response) => {
        console.log("Registration Response:", response); // ✅ Debug response

        if (response && response.user && response.jwtToken) {
          const roles = response.user.role.map((r: any) => r.roleName); // ✅ Extract only role names

          this.userAuthService.saveUserData(
            response.jwtToken,
            roles, // ✅ Store role names correctly
            response.user.userId,
            response.user.name
          );

          // ✅ Redirect based on roles
          if (roles.includes('Admin')) {
            this.router.navigate(['/books']);
          } else {
            this.router.navigate(['/borrow-book']);
          }
        }
      },
      (error) => {
        console.error("Registration Error:", error);
        this.errorMessage = error.error.message || "Registration failed. Please try again.";
      }
    );
  }
}
