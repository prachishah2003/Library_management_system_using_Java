package com.ibizabroker.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.entity.Books;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksController booksController;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Books book1;
    private Books book2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
        objectMapper = new ObjectMapper();

        book1 = new Books(1, "Book One", "Author One", "Fiction", 5);
        book2 = new Books(2, "Book Two", "Author Two", "Science", 3);
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() throws Exception {
        List<Books> booksList = Arrays.asList(book1, book2);
        when(booksRepository.findAll()).thenReturn(booksList);

        mockMvc.perform(get("/admin/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getBookById_ReturnsBook_WhenBookExists() throws Exception {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/admin/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value("Book One"));
    }

    @Test
    void getBookById_ReturnsNotFound_WhenBookDoesNotExist() throws Exception {
        when(booksRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_ReturnsCreatedBook() throws Exception {
        when(booksRepository.save(any(Books.class))).thenReturn(book1);

        mockMvc.perform(post("/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value("Book One"));
    }

    @Test
    void updateBook_ReturnsUpdatedBook_WhenBookExists() throws Exception {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));
        when(booksRepository.save(any(Books.class))).thenReturn(book1);

        mockMvc.perform(put("/admin/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value("Book One"));
    }

    @Test
    void deleteBook_ReturnsSuccessResponse_WhenBookExists() throws Exception {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));
        doNothing().when(booksRepository).delete(book1);

        mockMvc.perform(delete("/admin/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}

