package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.entity.Books;

import com.ibizabroker.lms.service.BookService;
import com.ibizabroker.lms.service.BorrowService;
import com.ibizabroker.lms.service.RequestService;
import com.ibizabroker.lms.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final BookService bookService;
    private final UserService userService;
    private final BorrowService borrowService;
    private final RequestService requestService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummaryStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", bookService.getTotalBooks());
        stats.put("totalUsers", userService.getTotalUsers());
        stats.put("borrowedBooks", borrowService.getTotalBorrowedBooks());
        stats.put("overdueBooks", borrowService.getTotalOverdueBooks());
        stats.put("requestedBooks", requestService.getTotalRequestedBooks());
        stats.put("returnRequests", requestService.getTotalReturnRequests());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/books-by-genre")
    public ResponseEntity<List<Map<String, Object>>> getBooksByGenre() {
        return ResponseEntity.ok(bookService.getBooksByGenre());
    }
    @GetMapping("/most-active-users")
    public ResponseEntity<List<Map<String, Object>>> getMostActiveUsers() {
        return ResponseEntity.ok(borrowService.getMostActiveUsers());
    }
    @GetMapping("/most-borrowed-genres")
    public ResponseEntity<List<Map<String, Object>>> getMostBorrowedGenres() {
        return ResponseEntity.ok(borrowService.getMostBorrowedGenres());
    }
    @GetMapping("/top-rated-books")
    public ResponseEntity<List<Books>> getTopRatedBooks() {
        return ResponseEntity.ok(bookService.getTopRatedBooks());
    }

    @GetMapping("/most-borrowed-books")
    public ResponseEntity<List<Map<String, Object>>> getMostBorrowedBooks() {
        return ResponseEntity.ok(borrowService.getMostBorrowedBooks());
    }

    @GetMapping("/books-borrowed-per-month")
    public ResponseEntity<List<Map<String, Object>>> getBooksBorrowedPerMonth() {
        return ResponseEntity.ok(borrowService.getBooksBorrowedPerMonth());
    }
}

