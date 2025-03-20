import { Component, HostBinding } from '@angular/core';
import { UsersService } from '../_service/users.service';
import { UserAuthService } from '../_service/user-auth.service';
import { SidebarService } from '../_service/sidebar.service'; // Import the SidebarService
import { Router, NavigationEnd } from '@angular/router';


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



  hideSidebar = false;

  

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Hide sidebar only on the "return-book" page
        this.hideSidebar = event.url === '/' || event.url === '/login' || event.url === '/register-user';


      }
    });
  }


  @HostBinding('class.collapsed')
  get isCollapsed(): boolean {
    return this.sidebarService.isCollapsed;
  }

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
