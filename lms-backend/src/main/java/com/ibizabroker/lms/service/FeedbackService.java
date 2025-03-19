package com.ibizabroker.lms.service;

import com.ibizabroker.lms.entity.Feedback;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.dao.FeedbackRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsersRepository usersRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UsersRepository usersRepository) {
        this.feedbackRepository = feedbackRepository;
        this.usersRepository = usersRepository;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public Feedback submitFeedback(Feedback feedback) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found")); // ✅ FIX HERE

        feedback.setUser(user);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteFeedback(Long feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) {
            feedbackRepository.deleteById(feedbackId);
        } else {
            throw new RuntimeException("Feedback not found");
        }
    }
}
