package com.ibizabroker.lms.dao;

import com.ibizabroker.lms.entity.Borrow;
import com.ibizabroker.lms.enums.ReturnReqStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByUserId(Integer userId);
    List<Borrow> findByBookId(Integer bookId);
    boolean existsByUserId(Integer userId);
    List<Borrow> findByReturnRequestStatus(ReturnReqStatus status);
    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.returnRequestStatus <> 'APPROVED'")
    long countUnreturnedBooks();

    long countByReturnRequestStatus(ReturnReqStatus status);
    @Query(value = "SELECT u.username AS username, COUNT(br.user_id) AS borrowCount FROM borrow br JOIN users u ON br.user_id = u.user_id GROUP BY u.username ORDER BY borrowCount DESC", nativeQuery = true)
    List<Map<String, Object>> findMostActiveUsers();

    @Query(value = "SELECT b.book_genre AS genre, COUNT(br.book_id) AS count FROM borrow br JOIN books b ON br.book_id = b.book_id GROUP BY b.book_genre ORDER BY count DESC", nativeQuery = true)
    List<Map<String, Object>> findMostBorrowedGenres();
    @Query("SELECT COUNT(br) FROM Borrow br WHERE br.dueDate < CURRENT_DATE AND br.returnRequestStatus <> :status")
    long countOverdueBooks(ReturnReqStatus status);

    @Query("SELECT b FROM Borrow b WHERE b.dueDate < CURRENT_DATE AND b.returnRequestStatus <> :status")
    List<Borrow> findOverdueBooks(ReturnReqStatus status);


    @Query(value = "SELECT b.book_name AS title, COUNT(br.book_id) AS count FROM borrow br JOIN books b ON br.book_id = b.book_id GROUP BY b.book_name ORDER BY count DESC", nativeQuery = true)
    List<Map<String, Object>> findMostBorrowedBooks();

    List<Borrow> findByDueDateBeforeAndReturnStatus(Date dueDate, String returnStatus);

    @Query(value = "SELECT EXTRACT(MONTH FROM issue_date) AS month, COUNT(*) AS count FROM borrow GROUP BY month ORDER BY month", nativeQuery = true)
    List<Map<String, Object>> findBooksBorrowedPerMonth();
    boolean existsByUserIdAndBookIdAndReturnRequestStatusIn(Integer userId, Integer bookId, List<ReturnReqStatus> statuses);
}
