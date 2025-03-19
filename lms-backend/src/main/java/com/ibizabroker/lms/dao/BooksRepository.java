package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query("SELECT b FROM Books b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :bookName, '%'))")
    List<Books> searchBooksByName(@Param("bookName") String bookName);
    List<Books> findByBookNameContainingIgnoreCase(String bookName);
}

