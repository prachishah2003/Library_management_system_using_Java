import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Books } from '../_model/books';
import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';
import { UserAuthService } from '../_service/user-auth.service';
import { RatingPopupComponent } from '../rating-popup/rating-popup.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-return-book',
    templateUrl: './return-book.component.html',
    styleUrls: ['./return-book.component.css'],
    standalone: false
})
export class ReturnBookComponent implements OnInit {

  books: Books[];
  borrow: Borrow[];

  constructor(
    private borrowService: BorrowService,
    private booksService: BooksService,
    private userAuthService: UserAuthService,
    private dialog: MatDialog 
  ) { }

  userId = this.userAuthService.getUserId();

  ngOnInit(): void {
    this.getBooks();
    this.getBooksByUser();
  }

  private getBooks() {
    this.booksService.getBooksList().subscribe(data =>{
      this.books = data;
    });
  }

  
  private getBooksByUser() {
    this.borrowService.getBooksBorrowedByUser(this.userId).subscribe(data => {
      this.borrow = data;
    })
  }

  brw: Borrow = new Borrow();
  public returnBook(borrowId: number, bookId: number) {
    this.brw.borrowId = borrowId;
  
    this.borrowService.returnBook(this.brw).subscribe(
      () => {
        console.log("Book returned successfully");
  
        // ✅ Show rating popup after returning the book
        const dialogRef = this.dialog.open(RatingPopupComponent, {
          width: '300px',
          data: { bookId: bookId }
        });
  
        dialogRef.afterClosed().subscribe(() => {
          console.log("Rating dialog closed");
  
          // ✅ Refresh both borrowed books and available books after returning
          this.getBooksByUser();
          this.getBooks();
        });
      },
      error => console.log(error)
    );
  }
  
  
  

}
