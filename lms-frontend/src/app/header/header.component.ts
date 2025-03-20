import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from '../_service/user-auth.service';
import { UsersService } from '../_service/users.service';
import { SidebarService } from '../_service/sidebar.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    standalone: false
})
export class HeaderComponent implements OnInit {

  constructor(
    private userAuthService: UserAuthService, 
    private router: Router,
    public userService: UsersService,
    public sidebarService: SidebarService
  ) { }

  
  get name(): string {
    return this.userAuthService.getName();
  }
  ngOnInit(): void {
    this.name || 'User';
  }

  public isLoggedIn() {
    console.log(this.name);
    return this.userAuthService.isLoggedIn();
  }

  public logout() {
    this.userAuthService.clear();
    this.router.navigate(['/']);
  }

  toggleSidebar() {
    this.sidebarService.toggleSidebar();
  }
  
}
