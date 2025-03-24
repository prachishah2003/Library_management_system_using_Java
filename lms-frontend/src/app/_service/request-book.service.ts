import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestedBook } from '../_model/requested-book';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestBookService {
  private baseURL = " http://localhost:8080";  

  constructor(private httpClient: HttpClient) {}

  // Request a new book (User)
  requestBook(bookName: string): Observable<RequestedBook> {
    return this.httpClient.post<RequestedBook>(`${this.baseURL}/api/requested-books`, { bookName }).pipe(
      catchError(this.handleError) // Handle errors
    );;
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Book already exists in the library';
    
    if (error.error instanceof ProgressEvent) {
      // Network error
      errorMessage = 'Network error: Please check your connection.';
    } else if (error.status === 400 && typeof error.error === 'string') {
      // Extract message from backend
      errorMessage = error.error;
    }

    return throwError(() => new Error(errorMessage));
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
