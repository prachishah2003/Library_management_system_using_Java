import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

import { Borrow } from '../_model/borrow';
import { BooksService } from '../_service/books.service';
import { BorrowService } from '../_service/borrow.service';


@Component({
  selector: 'app-borrow-list',
  templateUrl: './borrow-list.component.html',
  styleUrls: ['./borrow-list.component.css'],
  standalone: false
})
export class BorrowListComponent implements OnInit {
  borrows: Borrow[] = [];

  constructor(private borrowService: BorrowService) { }

  ngOnInit(): void {
    this.borrowService. getBorrowList().subscribe(data => {
      this.borrows = data;
    });
  }
}

