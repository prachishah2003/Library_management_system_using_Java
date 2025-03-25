package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.RequestedBook;
import com.ibizabroker.lms.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestedBookRepository extends JpaRepository<RequestedBook, Long> {

    @Query("SELECT r FROM RequestedBook r JOIN FETCH r.requestedBy")
    List<RequestedBook> findAllWithUsers();

    @Modifying
    @Transactional
    void deleteByRequestedBy(Users user); // If requestedBy is a User entity

    @Modifying
    @Transactional
    @Query("DELETE FROM RequestedBook r WHERE r.requestedBy.id = :userId")
    void deleteByRequestedById(Integer userId); // If requestedBy is a reference to an ID
}
