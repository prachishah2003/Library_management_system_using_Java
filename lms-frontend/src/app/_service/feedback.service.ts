import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback } from '../_model/feedback';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private baseUrl = 'http://localhost:8080/feedback';

  constructor(private http: HttpClient) {}

  submitFeedback(feedback: { feedbackText: string; rating: number }): Observable<Feedback> {
    return this.http.post<Feedback>(`${this.baseUrl}`, feedback);
  }

  getAllFeedbacks(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.baseUrl}`);
  }

  deleteFeedback(feedbackId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${feedbackId}`);
  }
}
