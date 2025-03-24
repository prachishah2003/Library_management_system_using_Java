export interface SummaryStats {
    totalBooks: number;
    totalUsers: number;
    borrowedBooks: number;
    overdueBooks: number;
    requestedBooks: number;
    returnRequests: number;
  }
  
  export interface GenreStats {
    genre: string;
    count: number;
  }
  
  export interface BorrowedPerMonth {
    month: string;
    count: number;
  }
  
  export interface UserActivity {
    username: string;
    borrowCount: number; // Adjusted from borrowcount
  }
  
  export interface BookStats {
    title: string; // Adjusted from bookName
    author?: string; // Optional field for books if needed
    genre?: string; // Adjusted from bookGenre
    imageUrl?: string; // Added to support book images
    noOfCopies?: number; // Added from backend response
    rating?: number; // Adjusted from averageRating
    count?: number; // For borrowed books
  }
  