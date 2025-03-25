package com.ibizabroker.lms.controller;


import com.ibizabroker.lms.service.BookRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin("http://localhost:4200/")
public class BookRatingController {
    private final BookRatingService bookRatingService;

    public BookRatingController(BookRatingService bookRatingService) {
        this.bookRatingService = bookRatingService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitRating(@RequestParam int bookId, @RequestParam int rating) {
        bookRatingService.saveRating(bookId, rating);
        return ResponseEntity.ok("Rating submitted successfully");
    }
}


