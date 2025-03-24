package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query("SELECT b FROM Books b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(b.bookAuthor) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(b.bookGenre) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Books> searchBooksByNameAuthorGenre(String query);
    List<Books> findByBookNameContainingIgnoreCase(String bookName);
    @Query("SELECT b.bookGenre AS genre, COUNT(b) AS count FROM Books b GROUP BY b.bookGenre")
    List<Map<String, Object>> countBooksByGenre();

    List<Books> findTop5ByOrderByAverageRatingDesc();
    boolean existsBybookName(String bookName);
}

