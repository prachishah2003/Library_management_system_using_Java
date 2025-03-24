package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.dao.RequestedBookRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.RequestedBook;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@CrossOrigin("http://localhost:4200/")

@RestController
@RequestMapping("/api/requested-books")
public class RequestedBookController {
    @Autowired
    private RequestedBookRepository repository;
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private UsersRepository userRepository;

    @PreAuthorize("hasRole('User')")
    @PostMapping
    public ResponseEntity<?> requestBook(@RequestBody RequestedBook bookRequest, Authentication authentication) {
        String username = authentication.getName(); // Get currently logged-in user

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        boolean bookExists = booksRepository.existsBybookName(bookRequest.getBookName());
        if (bookExists) {
            return ResponseEntity.badRequest().body("This book already exists in the library.");
        }
        RequestedBook newRequest = new RequestedBook();
        newRequest.setBookName(bookRequest.getBookName());
        newRequest.setRequestedBy(user); // Assign the logged-in user
        newRequest.setRequestedAt(new java.util.Date());

        RequestedBook savedRequest = repository.save(newRequest);
        return ResponseEntity.ok(savedRequest);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public List<RequestedBook> getAllRequests() {
        return repository.findAllWithUsers();
    }


    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Book request deleted");
    }
}

