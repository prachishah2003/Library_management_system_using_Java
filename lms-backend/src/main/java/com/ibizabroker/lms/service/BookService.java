package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.dao.BooksRepository;
import com.ibizabroker.lms.entity.*;
import com.ibizabroker.lms.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BookService {
    private final BooksRepository bookRepository;

    public long getTotalBooks() {
        return bookRepository.count();
    }

    public List<Map<String, Object>> getBooksByGenre() {
        return bookRepository.countBooksByGenre();
    }

    public List<Books> getTopRatedBooks() {
        return bookRepository.findTop5ByOrderByAverageRatingDesc();
    }
}

