import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {
  SummaryStats,
  GenreStats,
  BorrowedPerMonth,
  UserActivity,
  BookStats,
} from '../_model/stats.model';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/admin/statistics';

  constructor(private http: HttpClient) {}

  getSummaryStats(): Observable<SummaryStats> {
    return this.http.get<SummaryStats>(`${this.apiUrl}/summary`);
  }

  getBooksByGenre(): Observable<GenreStats[]> {
    return this.http.get<GenreStats[]>(`${this.apiUrl}/books-by-genre`);
  }

  getMostActiveUsers(): Observable<UserActivity[]> {
    return this.http.get<{ username: string; borrowcount: number }[]>(`${this.apiUrl}/most-active-users`).pipe(
      map(users =>
        users.map(user => ({
          username: user.username,
          borrowCount: user.borrowcount // Mapping 'borrowcount' to 'borrowCount'
        }))
      )
    );
  }

  getMostBorrowedGenres(): Observable<GenreStats[]> {
    return this.http.get<GenreStats[]>(`${this.apiUrl}/most-borrowed-genres`);
  }

  getTopRatedBooks(): Observable<BookStats[]> {
    return this.http.get<{ bookId: number; bookName: string; bookAuthor: string; bookGenre: string; imageUrl: string; noOfCopies: number; averageRating: number }[]>(`${this.apiUrl}/top-rated-books`).pipe(
      map(books =>
        books.map(book => ({
          title: book.bookName, // Mapping 'bookName' to 'title'
          author: book.bookAuthor, // Mapping 'bookAuthor' to 'author'
          genre: book.bookGenre, // Mapping 'bookGenre' to 'genre'
          imageUrl: book.imageUrl, // Keeping image URL
          noOfCopies: book.noOfCopies, // Keeping book copies
          rating: book.averageRating // Mapping 'averageRating' to 'rating'
        }))
      )
    );
  }

  getMostBorrowedBooks(): Observable<BookStats[]> {
    return this.http.get<{ title: string; count: number }[]>(`${this.apiUrl}/most-borrowed-books`);
  }

  getBooksBorrowedPerMonth(): Observable<BorrowedPerMonth[]> {
    return this.http.get<BorrowedPerMonth[]>(`${this.apiUrl}/books-borrowed-per-month`);
  }
}
