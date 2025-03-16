import { Component, OnInit } from '@angular/core';
import { Books } from '../_model/books';
import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';
import { UserAuthService } from '../_service/user-auth.service';
import { RequestedBook } from '../_model/requested-book';
import { RequestBookService } from '../_service/request-book.service';
import { FeedbackService } from '../_service/feedback.service';
import { Feedback } from '../_model/feedback';

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

  constructor(
    private booksService: BooksService,
    private userAuthService: UserAuthService,
    private borrowService: BorrowService,
    private requestedBookService: RequestBookService,
    private feedbackService: FeedbackService
  ) {
    this.userId = this.userAuthService.getUserId();
  }

  ngOnInit(): void {
    this.getBooks();
      }

  private getBooks() {
    this.booksService.getBooksList().subscribe(data => {
      this.books = data;
    });
  }

  borrowBook(bookId: number) {
    this.borrow.bookId = bookId;
    this.borrow.userId = this.userId;
    this.borrowService.borrowBook(this.borrow).subscribe(
      data => console.log(data),
      error => console.log(error)
    );
  }

  requestBook() {
    if (this.requestedBook.bookName) {
      this.requestedBookService.requestBook(this.requestedBook.bookName).subscribe(
        data => {
          alert('Book request submitted successfully! for \n' + this.requestedBook.bookName);
          this.requestedBook.bookName = ''; // Reset the input field
        },
        error => alert('Error submitting request.')
      );
    }
  }

  // Feedback Methods
  setRating(star: number) {
    this.rating = star;
  }

  feedbackSuccess: string = '';
  feedbackError: string = '';
  
  submitFeedback() {
    if (!this.feedbackText || this.rating === 0) {
      this.feedbackError = 'Please enter feedback and select a rating.';
      this.feedbackSuccess = ''; // Clear success message
      return;
    }
  
    const feedbackData = {
      feedbackText: this.feedbackText,
      rating: this.rating
    };
  
    this.feedbackService.submitFeedback(feedbackData).subscribe(
      () => {
        this.feedbackSuccess = 'Feedback submitted successfully!';
        this.feedbackError = ''; // Clear error message
        this.feedbackText = '';
        this.rating = 0;
      },
      () => {
        this.feedbackError = 'Could not submit. Please try again.';
        this.feedbackSuccess = ''; // Clear success message
      }
    );
  }
 


 
}
