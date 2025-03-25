import { Component,Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-rating-popup',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatButtonModule
  ],
  templateUrl: './rating-popup.component.html',
  styleUrls: ['./rating-popup.component.css']
})


export class RatingPopupComponent {
  rating: number = 5;
  stars = [1, 2, 3, 4, 5];
  hoverStar: number = 0;

  constructor(
    public dialogRef: MatDialogRef<RatingPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { bookId: number; userId: number },
    private http: HttpClient,
    @Inject(ChangeDetectorRef) private cdr: ChangeDetectorRef
  ) {}

  selectRating(star: number) {
    this.rating = star;
  }
  hoverRating(star: number) {
    this.hoverStar = star;
  }

  // ⭐ Reset Hover Effect
  leaveRating() {
    this.hoverStar = 0;
  }
  submitRating() {
    const params = new HttpParams()
      .set('bookId', this.data.bookId)
      .set('rating', this.rating); // Remove userId
  
      this.http.post('http://localhost:8080/api/ratings/submit', params).subscribe({
        next: () => {
          this.dialogRef.close();
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.dialogRef.close();
          this.cdr.detectChanges();
        }
      });
  }
  

  skip() {
    this.dialogRef.close();
  }
}
