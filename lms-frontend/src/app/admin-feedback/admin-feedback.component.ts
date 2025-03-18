import { Component, OnInit } from '@angular/core';
import { Feedback } from '../_model/feedback';
import { ActivatedRoute } from '@angular/router';
import { FeedbackService } from '../_service/feedback.service';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-admin-feedback',
  templateUrl: './admin-feedback.component.html',
  styleUrls: ['./admin-feedback.component.css'],
  standalone:false
})
export class AdminFeedbackComponent implements OnInit {
  feedbacks: any[];

  constructor(private feedbackService: FeedbackService,private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.getAllFeedbacks();
  }

  private getAllFeedbacks() {
    this.feedbackService.getAllFeedbacks().subscribe(
      (data) => {
        this.feedbacks = data;
      },
      (error) => {
        console.error('Error fetching feedbacks', error);
      }
    );
  }

  deleteFeedback(id?: number) {
    if (id !== undefined) {
      this.feedbackService.deleteFeedback(id).subscribe(() => {
        this.feedbacks = this.feedbacks.filter(f => f.id !== id);
        this.cdr.detectChanges();
      });
    }
  }
}
