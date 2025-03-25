import { Component, OnInit } from '@angular/core';
import { BorrowService} from '../_service/borrow.service';
import { Borrow } from '../_model/borrow';

@Component({
  selector: 'app-admin-overdue-books',
  templateUrl: './admin-overdue-books.component.html',
  styleUrls: ['./admin-overdue-books.component.css'],
  standalone:false
})
export class AdminOverdueBooksComponent implements OnInit {
  overdueBooks:Borrow[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(private borrowService: BorrowService) {}

  ngOnInit(): void {
    this.fetchOverdueBooks();
  }

  fetchOverdueBooks(): void {
    this.borrowService.getOverdueBooks().subscribe({
      next: (books) => {
        this.overdueBooks = books;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load overdue books.';
        this.loading = false;
      }
    });
  }

  calculateOverdueDays(dueDate: Date): number {
    const due = new Date(dueDate);
    const today = new Date();
    return Math.floor((today.getTime() - due.getTime()) / (1000 * 60 * 60 * 24));
  }
}
