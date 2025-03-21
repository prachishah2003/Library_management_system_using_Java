package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.Borrow;
import com.ibizabroker.lms.enums.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByUserId(Integer userId);
    List<Borrow> findByBookId(Integer bookId);
    boolean existsByUserId(Integer userId);
    List<Borrow> findByReturnRequestStatus(ReturnStatus status);

}
