package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Books;
import com.ibizabroker.lms.entity.Borrow;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowControllerTest {

    @InjectMocks
    private BorrowController borrowController;

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BooksRepository booksRepository;

    private Users mockUser;
    private Books mockBook;
    private Borrow mockBorrow;

    @BeforeEach
    void setUp() {
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setName("John Doe");

        mockBook = new Books();
        mockBook.setBookId(1);
        mockBook.setBookName("Spring Boot in Action");
        mockBook.setNoOfCopies(3);

        mockBorrow = new Borrow();
        mockBorrow.setBorrowId(1);
        mockBorrow.setUserId(mockUser.getUserId());
        mockBorrow.setBookId(mockBook.getBookId());
    }

    @Test
    void testBorrowBook_Success() {
        when(usersRepository.findById(mockBorrow.getUserId())).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(mockBorrow.getBookId())).thenReturn(Optional.of(mockBook));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(mockBorrow);

        String response = borrowController.borrowBook(mockBorrow);
        assertNotNull(response);
        assertTrue(response.contains("has borrowed one copy of"));
    }

    @Test
    void testBorrowBook_UserNotFound() {
        when(usersRepository.findById(mockBorrow.getUserId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> borrowController.borrowBook(mockBorrow));
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(usersRepository.findById(mockBorrow.getUserId())).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(mockBorrow.getBookId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> borrowController.borrowBook(mockBorrow));
        assertTrue(exception.getMessage().contains("Book not found"));
    }

    @Test
    void testBorrowBook_NoCopiesAvailable() {
        mockBook.setNoOfCopies(0);
        when(usersRepository.findById(mockBorrow.getUserId())).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(mockBorrow.getBookId())).thenReturn(Optional.of(mockBook));

        String response = borrowController.borrowBook(mockBorrow);
        assertEquals("The book \"Spring Boot in Action\" is out of stock!", response);
    }

    @Test
    void testReturnBook_Success() {
        when(borrowRepository.findById(mockBorrow.getBorrowId())).thenReturn(Optional.of(mockBorrow));
        when(booksRepository.findById(mockBorrow.getBookId())).thenReturn(Optional.of(mockBook));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(mockBorrow);

        Borrow returnedBorrow = borrowController.returnBook(mockBorrow);
        assertNotNull(returnedBorrow.getReturnDate());
    }

    @Test
    void testGetAllBorrows() {
        List<Borrow> borrowList = List.of(mockBorrow);
        when(borrowRepository.findAll()).thenReturn(borrowList);

        List<Borrow> result = borrowController.getAllBorrow();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testBooksBorrowedByUser() {
        List<Borrow> borrowList = List.of(mockBorrow);
        when(borrowRepository.findByUserId(mockUser.getUserId())).thenReturn(borrowList);

        List<Borrow> result = borrowController.booksBorrowedByUser(mockUser.getUserId());
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}

