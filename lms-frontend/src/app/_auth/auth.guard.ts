import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserAuthService } from '../_service/user-auth.service';
import { UsersService } from '../_service/users.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard  {

  constructor(private userAuthService: UserAuthService,
    private router: Router,
    private userService: UsersService
  ) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      if (this.userAuthService.getToken()) {
        const allowedRoles = route.data['roles'] as string[];
        console.log("Allowed Roles:", allowedRoles); // ✅ Debug allowed roles from route
        console.log("User Roles:", this.userAuthService.getRoles()); // ✅ Debug actual user roles

        if (!allowedRoles || this.userService.roleMatch(allowedRoles)) {
          return true;
        } else {
          this.router.navigate(['/forbidden']);
          return false;
        }
      }
      this.router.navigate(['/login']);
      return false;
  }
}
