import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Books } from '../_model/books'
import { BooksService } from '../_service/books.service';

@Component({
    selector: 'app-books-list',
    templateUrl: './books-list.component.html',
    styleUrls: ['./books-list.component.css'],
    standalone: false
})
export class BooksListComponent implements OnInit {

  books: Books[];

  constructor(private booksService: BooksService,
    private router: Router) { }

  

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
