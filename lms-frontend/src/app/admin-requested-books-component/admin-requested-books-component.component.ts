import { Component, OnInit } from '@angular/core';
import { RequestBookService } from '../_service/request-book.service';
import { RequestedBook } from '../_model/requested-book';
import { ChangeDetectorRef } from '@angular/core';

@Component({
    selector: 'app-admin-requested-books',
    templateUrl: './admin-requested-books-component.component.html',
    styleUrls: ['./admin-requested-books-component.component.css'],
    standalone: false
})
export class AdminRequestedBooksComponent implements OnInit {
  requestedBooks: any[] ;

  constructor(private requestBookService: RequestBookService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadRequestedBooks();
  }
  

  loadRequestedBooks() {
    this.requestBookService.getRequestedBooks().subscribe(
      (data) => {
        console.log("Requested Books:", data);
        this.requestedBooks = data; // Ensure this is assigned
      },
      (error) => {
        console.error("Error fetching requested books:", error);
      }
    );
    
  }

  deleteRequest(id: number) {
    if (confirm('Are you sure you want to delete this request?')) {
      this.requestBookService.deleteRequestedBook(id).subscribe({
        next: () => {
          this.requestedBooks = this.requestedBooks.filter(request => request.id !== id);
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error deleting request', error);
        }
      });
    }
  }
}
