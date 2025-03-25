import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Books } from '../_model/books'
import { BooksService } from '../_service/books.service';
import { debounceTime, Subject } from 'rxjs';

@Component({
    selector: 'app-books-list',
    templateUrl: './books-list.component.html',
    styleUrls: ['./books-list.component.css'],
    standalone: false
})
export class BooksListComponent implements OnInit {

  books: Books[];
  searchQuery: string = '';
 
  constructor(private booksService: BooksService,
    private router: Router) { }

  

  ngOnInit(): void {
    this.getBooks();
   
  }

  onSearch(event: KeyboardEvent) {
    if (event.key === "Enter") {
      console.log("Searching for:", this.searchQuery); // Debugging
      this.performSearch();
    }
  }

  performSearch() {
    if (this.searchQuery.trim().length < 1) {
      this.getBooks(); // Load all books if search is empty
      return;
    }
    this.booksService.searchBooks(this.searchQuery).subscribe({
      next: (data) => {
        this.books = data;
      },
      error: (error) => {
        console.error("Error fetching search results:", error);
      }
    });
  }

  private getBooks() {
    this.booksService.getBooksList().subscribe(data =>{
      this.books = data;
    });
  }

  updateBook(bookId: number) {
    this.router.navigate(['update-book', bookId ]);
  }

  deleteBook(bookId: number) {
    this.booksService.deleteBook(bookId).subscribe( data=> {
      this.getBooks();
    });
  }

  bookDetails(bookId: number) {
    this.router.navigate(['book-details', bookId ]);
  }

}
