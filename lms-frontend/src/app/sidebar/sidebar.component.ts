import { Component } from '@angular/core';
import { UsersService } from '../_service/users.service';
import { UserAuthService } from '../_service/user-auth.service';
import { Router } from '@angular/router';
import { SidebarService } from '../_service/sidebar.service'; // Import the SidebarService

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  standalone: false
})
export class SidebarComponent {
  adminMenuOpen = false;

  constructor(
    public userService: UsersService, 
    private userAuthService: UserAuthService, 
    private router: Router,
    public sidebarService: SidebarService // Use SidebarService
  ) {}

  toggleAdminMenu() {
    this.adminMenuOpen = !this.adminMenuOpen;
  }

  isLoggedIn(): boolean {
    return this.userAuthService.isLoggedIn();
  }

  public logout() {
    this.userAuthService.clear();
    this.router.navigate(['/']);
  }

  get name(): string {
    return this.userAuthService.getName();
  }
}
