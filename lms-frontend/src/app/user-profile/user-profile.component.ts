import { Component, OnInit } from '@angular/core';
import { UsersService } from '../_service/users.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  standalone:false
})
export class UserProfileComponent implements OnInit {
  user: any = {};
  amount: number = 0;
  message: string = "";
  messageType: string = "";

  constructor(private userService: UsersService) {}

  ngOnInit() {
    this.getUserDetails();
  }

  getUserDetails() {
    this.userService.getUser().subscribe(data => {
      this.user = data;
    });
  }

  addBalance() {
    if (this.amount <= 0) {
      this.showMessage("Please enter a valid amount.", "danger");
      return;
    }

    this.userService.addBalance(this.user.userId, this.amount).subscribe(updatedUser => {
      this.user.accountBalance = updatedUser.accountBalance;
      this.amount = 0;
      this.showMessage("Balance updated successfully!", "success");
    });
  }

  showMessage(message: string, type: string) {
    this.message = message;
    this.messageType = type;

    setTimeout(() => {
      this.message = ""; // Clear message after 3 seconds
    }, 3000);
  }
}
