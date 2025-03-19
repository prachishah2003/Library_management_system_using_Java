import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../_service/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
  standalone: false
})
export class AddUserComponent implements OnInit {
  addUserForm!: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private userService: UsersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.addUserForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  addUser() {
    if (this.addUserForm.invalid) {
      return;
    }

    this.userService.addUser(this.addUserForm.value).subscribe(
      (response) => {
        console.log("User added successfully:", response);
        this.router.navigate(['/users']); // Redirect after success
      },
      (error) => {
        console.error("Error adding user:", error);
        this.errorMessage = error.error.message || "Failed to add user.";
      }
    );
  }
}
