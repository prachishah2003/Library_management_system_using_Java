import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../_service/dashboard.service';
import {
  SummaryStats,
  GenreStats,
  BorrowedPerMonth,
  UserActivity,
  BookStats,
} from '../_model/stats.model';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  standalone:false
})
export class AdminDashboardComponent implements OnInit {
  summaryStats: SummaryStats | null = null;
  booksByGenreChartData: any;
  mostBorrowedGenresChartData: any;
  booksBorrowedPerMonthChartData: any;
  mostBorrowedGenres: GenreStats[] = [];
  mostBorrowedBooks: BookStats[] = [];
  mostActiveUsers: UserActivity[] = [];
  topRatedBooks: BookStats[] = [];

  chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
  };

  constructor(private dashboardService: DashboardService) {}

  ngOnInit() {
    this.loadSummaryStats();
    this.loadCharts();
    this.loadTables();
  }

  loadSummaryStats() {
    this.dashboardService.getSummaryStats().subscribe((data) => {
      this.summaryStats = data;
    });
  }

  loadCharts() {
    // Books by Genre (Pie Chart)
    this.dashboardService.getBooksByGenre().subscribe((data) => {
      this.booksByGenreChartData = {
        labels: data.map((genre) => genre.genre),
        datasets: [
          {
            label: 'Number of Books',
            data: data.map((genre) => genre.count),
            backgroundColor: '#f1b787',
          },
        ],
      };
    });

   
    // Books Borrowed Per Month (Bar Chart)
    this.dashboardService.getBooksBorrowedPerMonth().subscribe((data) => {
      this.booksBorrowedPerMonthChartData = {
        labels: data.map((month) => month.month),
        datasets: [
          {
            label: 'Books Borrowed',
            data: data.map((month) => month.count),
            backgroundColor: '#f1b787',
          },
        ],
      };
    });
  }

  loadTables() {
    // Most Borrowed Books (Table)
    this.dashboardService.getMostBorrowedBooks().subscribe((data) => {
      this.mostBorrowedBooks = data;
    });

    // Most Active Users (Table)
    this.dashboardService.getMostActiveUsers().subscribe((data) => {
      this.mostActiveUsers = data;
    });
        // Most Borrowed Genres (Pie Chart & Table)
        this.dashboardService.getMostBorrowedGenres().subscribe((data) => {
          this.mostBorrowedGenres = data;})
    // Top Rated Books (Table)
    this.dashboardService.getTopRatedBooks().subscribe((data) => {
      this.topRatedBooks = data;
    });
  }
}