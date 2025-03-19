import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Borrow } from '../_model/borrow';

@Injectable({
  providedIn: 'root'
})
export class BorrowService {

  private baseURL = "http://localhost:8080/borrow";

  constructor(private httpClient: HttpClient) { }

  getBorrowList(): Observable<Borrow[]> {
    return this.httpClient.get<Borrow[]>(`${this.baseURL}`);
  }

  borrowBook(borrow: Borrow): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, borrow);
  }

  requestReturn(borrowId: number): Observable<any> {
    return this.httpClient.put(`${this.baseURL}/request-return`, { borrowId });
  }

  getPendingReturns(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.baseURL}/admin/pending-returns`);
  }

  approveReturn(borrowId: number): Observable<any> {
    return this.httpClient.put(`${this.baseURL}/admin/approve-return/${borrowId}`, {});
  }

  rejectReturn(borrowId: number): Observable<any> {
    return this.httpClient.put(`${this.baseURL}/admin/reject-return/${borrowId}`, {});
  }

  getBooksBorrowedByUser(userId: number): Observable<Borrow[]> {
    return this.httpClient.get<Borrow[]>(`${this.baseURL}/user/${userId}`);
  }

  getBookBorrowHistory(bookId: number): Observable<Borrow[]> {
    return this.httpClient.get<Borrow[]>(`${this.baseURL}/book/${bookId}`);
  }
}
