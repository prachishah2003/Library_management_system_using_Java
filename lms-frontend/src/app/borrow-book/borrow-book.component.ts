import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Books } from '../_model/books';
import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';
import { UserAuthService } from '../_service/user-auth.service';
import { RequestedBook } from '../_model/requested-book';
import { RequestBookService } from '../_service/request-book.service';
import { FeedbackService } from '../_service/feedback.service';
import { Feedback } from '../_model/feedback';
import { ChangeDetectorRef } from '@angular/core';

declare var bootstrap: any;

@Component({
  selector: 'app-borrow-book',
  templateUrl: './borrow-book.component.html',
  styleUrls: ['./borrow-book.component.css'],
  standalone: false
})
export class BorrowBookComponent implements OnInit {
  books: Books[];
  borrow: Borrow = new Borrow();
  requestedBook: RequestedBook = new RequestedBook();
  userId: number;

  // Feedback
  feedbackText: string = '';
  rating: number = 0;
  feedbacks: Feedback[] = [];

  // ✅ Ensure these properties exist
  feedbackSuccess: string = '';
  feedbackError: string = '';
  borrowSuccess: string = '';
  borrowError: string = '';
  requestSuccess: string = '';  // ✅ Added this
  requestError: string = '';    // ✅ Added this

  constructor(
    private booksService: BooksService,
    private userAuthService: UserAuthService,
    private borrowService: BorrowService,
    private requestedBookService: RequestBookService,
    private feedbackService: FeedbackService,
    private cdr: ChangeDetectorRef
  ) {
    this.userId = this.userAuthService.getUserId();
  }

  ngOnInit(): void {
    this.getBooks();
  }

  searchQuery: string = '';

  onSearch(event: KeyboardEvent) {
    if (event.key === "Enter") {
        this.booksService.searchBooks(this.searchQuery).subscribe({
            next: (data) => {
                this.books = data;
            },
            error: (error) => {
                console.error("Error fetching search results:", error);
            }
        });
    }
}
  private getBooks() {
    this.booksService.getBooksList().subscribe(data => {
      this.books = data;
    });
  }
  hideMessages() {
    setTimeout(() => {
      this.borrowSuccess = '';
      this.borrowError = '';
      this.requestSuccess = '';
      this.requestError = '';
      this.feedbackSuccess = '';
      this.feedbackError = '';
    }, 3000); // 3 seconds
  }

  borrowBook(bookId: number) {
    this.borrow.bookId = bookId;
    this.borrow.userId = this.userId;
    this.borrowService.borrowBook(this.borrow).subscribe(
      () => {
        this.borrowSuccess = 'Book borrowed successfully!';
        this.borrowError = ''; 
        this.getBooks(); 
        this.hideMessages();
      },
      () => {
        this.borrowError = 'Error borrowing book. Please try again.';
        this.borrowSuccess = ''; 
      }
    );
  }

  requestBook() {
    if (this.requestedBook.bookName) {
      this.requestedBookService.requestBook(this.requestedBook.bookName).subscribe(
        () => {
          this.requestSuccess = 'Book request submitted successfully!';
          this.requestError = ''; 
          this.requestedBook.bookName = ''; 
          this.hideMessages();
        },
        () => {
          this.requestError = 'Error submitting request.';
          this.requestSuccess = ''; 
        }
      );
    }
  }

 
  setRating(star: number) {
    this.rating = star;
  }

  submitFeedback() {
    if (!this.feedbackText || this.rating === 0) {
      this.feedbackError = 'Please enter feedback and select a rating.';
      this.feedbackSuccess = ''; 
      return;
    }

    const feedbackData = {
      feedbackText: this.feedbackText,
      rating: this.rating
    };

    this.feedbackService.submitFeedback(feedbackData).subscribe(
      () => {
        this.feedbackSuccess = 'Feedback submitted successfully!';
        this.feedbackError = ''; 
        this.feedbackText = '';
        this.rating = 0;
        this.hideMessages();
      },
      () => {
        this.feedbackError = 'Could not submit. Please try again.';
        this.feedbackSuccess = ''; 
      }
    );
  }
}
