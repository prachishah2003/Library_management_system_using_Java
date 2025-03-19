package com.ibizabroker.lms.service;

import com.ibizabroker.lms.entity.BookRating;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.entity.Books;
import com.ibizabroker.lms.dao.BookRatingRepository;
import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class BookRatingService {
    private final BookRatingRepository bookRatingRepository;
    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;

    public BookRatingService(BookRatingRepository bookRatingRepository, BooksRepository booksRepository, UsersRepository usersRepository) {
        this.bookRatingRepository = bookRatingRepository;
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public void saveRating(int bookId, int rating) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookRating bookRating = new BookRating();
        bookRating.setBook(book);
        bookRating.setUser(user);  // ✅ Set user correctly
        bookRating.setRating(rating);
        bookRatingRepository.save(bookRating);

        // Calculate the new average rating
        updateAverageRating(book);
    }

    private void updateAverageRating(Books book) {
        List<BookRating> ratings = bookRatingRepository.findByBook_BookId(book.getBookId());
        double avgRating = ratings.stream()
                .mapToInt(BookRating::getRating)
                .average()
                .orElse(0.0);
        book.setAverageRating(avgRating);
        booksRepository.save(book);
    }
}


