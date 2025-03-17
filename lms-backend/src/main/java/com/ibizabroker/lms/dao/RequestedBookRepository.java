package com.ibizabroker.lms.dao;
import com.ibizabroker.lms.entity.RequestedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RequestedBookRepository extends JpaRepository<RequestedBook, Long> {
    @Query("SELECT r FROM RequestedBook r JOIN FETCH r.requestedBy")
    List<RequestedBook> findAllWithUsers();

}

