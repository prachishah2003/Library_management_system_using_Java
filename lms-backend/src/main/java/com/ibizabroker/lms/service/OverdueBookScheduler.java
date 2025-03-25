package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Borrow;
import com.ibizabroker.lms.entity.Users;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OverdueBookScheduler {

    private final BorrowRepository borrowRepository;
    private final UsersRepository userRepository;

    public OverdueBookScheduler(BorrowRepository borrowRepository, UsersRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    @Transactional
    public void markOverdueBooksAndDeductFine() {
        Date today = new Date(); // Current date

        // Fetch all books that are overdue and still in "BORROWED" status
        List<Borrow> overdueBooks = borrowRepository.findByDueDateBeforeAndReturnStatus(today, "BORROWED");

        for (Borrow borrow : overdueBooks) {
            Date dueDate = borrow.getDueDate();
            long overdueDays = calculateOverdueDays(dueDate, today);
            double fine = overdueDays * 10; // ₹10 per day

            // Fetch user safely
            Optional<Users> optionalUser = userRepository.findById(borrow.getUserId());
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();

                // Deduct fine if balance is sufficient
                if (user.getAccountBalance() >= fine) {
                    user.setAccountBalance(user.getAccountBalance() - fine);
                    borrow.setFine(fine);
                    borrow.setReturnStatus("OVERDUE");

                    userRepository.save(user);
                    borrowRepository.save(borrow);
                    System.out.println("Fine of ₹" + fine + " deducted from " + user.getUsername());
                } else {
                    System.out.println("Insufficient balance for " + user.getUsername() + " to deduct fine of ₹" + fine);
                }
            } else {
                System.out.println("User with ID " + borrow.getUserId() + " not found!");
            }
        }
    }

    /**
     * Calculates the number of overdue days between due date and today.
     */
    private long calculateOverdueDays(Date dueDate, Date today) {
        long diffInMillies = today.getTime() - dueDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
