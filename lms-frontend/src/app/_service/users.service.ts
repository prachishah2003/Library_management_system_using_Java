import { HttpClient, HttpHeaders , HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Users } from '../_model/users';
import { NgForm } from '@angular/forms';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private baseURL = "http://localhost:8080"; // Backend URL
  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  // Register New User
  public register(userData: any): Observable<any> {
    return this.httpClient.post(`${this.baseURL}/auth/register`, userData, {
      headers: this.requestHeader
    });
  }
  
  public roleMatch(allowedRoles: string[]): boolean {
    const userRoles = this.userAuthService.getRoles();
    return allowedRoles.some(role => userRoles.includes(role)); // Keep case-sensitive comparison
  }
  
  
  
  // Login User
  public login(loginData: NgForm) {
    return this.httpClient.post("http://localhost:8080/authenticate", loginData, {
      headers: this.requestHeader,
    });
  }

  public getUsersList(): Observable<any[]> {
    return this.httpClient.get<Users[]>(`${this.baseURL}/admin/users`);
  }

  public addUser(user: any): Observable<any> {
    return this.httpClient.post<any>(`${this.baseURL}/admin/users`, user);
  }

  // ✅ GET User by ID
  public getUserById(userId: number): Observable<any> {
    return this.httpClient.get<Users>(`${this.baseURL}/admin/users/${userId}`);
  }

  // ✅ UPDATE User
  public updateUser(userId: number, userData: any): Observable<any> {
    return this.httpClient.put(`${this.baseURL}/admin/users/${userId}`, userData);
  }

  // ✅ DELETE User
  deleteUser(userId: number): Observable<string> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.httpClient.delete<string>(`${this.baseURL}/admin/users`, { params });
  }
}
