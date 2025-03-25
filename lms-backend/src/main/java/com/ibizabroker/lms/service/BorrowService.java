package com.ibizabroker.lms.service;
import com.ibizabroker.lms.dao.BorrowRepository;

import com.ibizabroker.lms.entity.Borrow;
import com.ibizabroker.lms.enums.ReturnReqStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public String borrowBook(Integer userId, Integer bookId) {
        // Check if the user already has this book and has not returned it
        List<ReturnReqStatus> activeStatuses = Arrays.asList(ReturnReqStatus.NONE, ReturnReqStatus.PENDING, ReturnReqStatus.REJECTED);
        boolean alreadyBorrowed = borrowRepository.existsByUserIdAndBookIdAndReturnRequestStatusIn(userId, bookId, activeStatuses);

        if (alreadyBorrowed) {
            throw new IllegalStateException("You have already borrowed this book and must return it before borrowing again.");
        }

        // Proceed with borrowing the book
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        borrow.setIssueDate(new Date());
        borrow.setDueDate(calculateDueDate());
        borrow.setReturnRequestStatus(ReturnReqStatus.PENDING);

        borrowRepository.save(borrow);
        return "Book borrowed successfully!";
    }

    private Date calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14); // 2-week borrowing period
        return calendar.getTime();
    }

    public long getTotalBorrowedBooks() {
        return borrowRepository.countUnreturnedBooks();
    }
    public List<Borrow> getOverdueBooks() {
        return borrowRepository.findOverdueBooks(ReturnReqStatus.APPROVED);
    }
    public List<Map<String, Object>> getMostActiveUsers() {
        return borrowRepository.findMostActiveUsers();
    }
    public List<Map<String, Object>> getMostBorrowedGenres() {
        return borrowRepository.findMostBorrowedGenres();
    }
    public long getTotalOverdueBooks() {
        long overdueBooks = borrowRepository.countOverdueBooks(ReturnReqStatus.APPROVED);

        return overdueBooks;
    }

    public List<Map<String, Object>> getMostBorrowedBooks() {
        return borrowRepository.findMostBorrowedBooks();
    }

    public List<Map<String, Object>> getBooksBorrowedPerMonth() {
        return borrowRepository.findBooksBorrowedPerMonth();
    }
}

