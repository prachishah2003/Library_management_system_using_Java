import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestedBook } from '../_model/requested-book';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestBookService {
  private baseURL = " http://localhost:8080";  

  constructor(private httpClient: HttpClient) {}

  // Request a new book (User)
  requestBook(bookName: string): Observable<RequestedBook> {
    return this.httpClient.post<RequestedBook>(`${this.baseURL}/api/requested-books`, { bookName });
  }

  // Get all requested books (Admin)
  
  getRequestedBooks(): Observable<RequestedBook[]> {
    return this.httpClient.get<any[]>(`${this.baseURL}/api/requested-books`);
  }

  // Delete a book request (Admin)
  deleteRequestedBook(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseURL}/api/requested-books/${id}`);
  }
}
