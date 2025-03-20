import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {
  constructor() {}

// Store Roles
public setRoles(roles: string[]) {
  localStorage.setItem('roles', JSON.stringify(roles));
}

// Retrieve Roles
public getRoles(): string[] {
  return JSON.parse(localStorage.getItem('roles') || '[]');
}


  // Store JWT Token with "Bearer " Prefix
  public setToken(jwtToken: string) {
    
    localStorage.setItem('jwtToken', jwtToken);
  }

  // Retrieve Token Safely
  public getToken(): string  {
    return localStorage.getItem('jwtToken')!;
  }

  // Store User ID
  public setUserId(userId: number) {
    localStorage.setItem('userId', JSON.stringify(userId));
  }

  // Retrieve User ID Safely
  public getUserId(): number  {
    return JSON.parse(localStorage.getItem('userId')!);
  }

  // Store User Name
  public setName(name: string) {
    localStorage.setItem('name', name);
  }

  // Retrieve User Name Safely
  public getName(): string {
    return localStorage.getItem('name') || 'Unknown User';
  }

  // Clear User Data
  public clear() {
    localStorage.clear();
  }

  // Check if User is Logged In
  public isLoggedIn(): boolean {
    return !!this.getToken() && this.getRoles().length > 0;
  }

  // Save All User Data in One Method
  public saveUserData(token: string, roles: any[], userId: number, name: string) {
    this.setToken(token);
    this.setRoles(roles);
    this.setUserId(userId);
    this.setName(name);
  }
}
