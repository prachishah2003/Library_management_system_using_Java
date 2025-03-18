package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
    List<BookRating> findByBook_BookId(int bookId);
}

