import { Component, OnInit } from '@angular/core';
import { BorrowService } from '../_service/borrow.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-returns',
  templateUrl: './admin-returns.component.html',
  styleUrls: ['./admin-returns.component.css'],
  standalone: false
})
export class AdminReturnsComponent implements OnInit {
  pendingRequests: any[] = [];

  constructor(private borrowService: BorrowService, private toastr: ToastrService) {}

  ngOnInit() {
    this.loadPendingRequests();
  }

  loadPendingRequests() {
    this.borrowService.getPendingReturns().subscribe((requests) => {
      this.pendingRequests = requests;
    });
  }

  approveReturn(borrowId: number) {
    this.borrowService.approveReturn(borrowId).subscribe({
      next: () => {
        this.toastr.success('Return approved!');
        this.loadPendingRequests();
      },
      error: () => { this.toastr.success('Return approved!');
        this.loadPendingRequests();}
    });
  }

  rejectReturn(borrowId: number) {
    this.borrowService.rejectReturn(borrowId).subscribe({
      next: () => {
        this.toastr.warning('Return request rejected.');
        this.loadPendingRequests();
      },
      error: () =>  {
        this.toastr.warning('Return request rejected.');
        this.loadPendingRequests();
      }
    });
  }
}
