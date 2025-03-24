import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Books } from '../_model/books';
import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';
import { UserAuthService } from '../_service/user-auth.service';
import { RatingPopupComponent } from '../rating-popup/rating-popup.component';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

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
    private dialog: MatDialog,
    private toastr: ToastrService
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
  public requestReturn(borrowId: number, bookId: number) {
    const dialogRef = this.dialog.open(RatingPopupComponent, {
      width: '300px',
      data: { bookId: bookId }
    });

    // Refresh book lists after rating popup is closed
    dialogRef.afterClosed().subscribe(() => {
      console.log("Rating dialog closed");
      this.getBooksByUser(); // Refresh borrowed books list
    });
    this.borrowService.requestReturn(borrowId).subscribe({
      next: (response) => {
        this.toastr.success('Return request submitted successfully', 'Success');
  
        // Open rating popup
        
  
        this.getBooks(); // Refresh available books list if necessary
      },
      error: (error) => {
        this.toastr.success('Return request submitted successfully', 'Success');
      }
    });
  }
  
  

}
