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
    this.requestBookService.getRequestedBooks().subscribe({
      next: (data) => {
        console.log('Fetched requested books:', data); // ✅ Debugging log
        this.requestedBooks = data; // ✅ Update UI with fresh data
        this.cdr.detectChanges(); // ✅ Ensure UI updates
      },
      error: (error) => {
        console.error('Error fetching requested books:', error);
      }
    });
  }
  

  deleteRequest(id: number) {
    if (confirm('Are you sure you want to delete this request?')) {
      this.requestBookService.deleteRequestedBook(id).subscribe({
        next: () => {
          // Remove the deleted request from the local array
          this.requestedBooks = this.requestedBooks.filter(request => request.id !== id);
          
          // Re-fetch the updated list from the backend
          this.loadRequestedBooks();
  
          // Ensure UI updates properly with a slight delay
          setTimeout(() => this.cdr.detectChanges(), 0);
        },
        error: (error) => {
          console.error('Error deleting request', error);
          this.loadRequestedBooks();
        }
      });
    }
  }
  
  
}
