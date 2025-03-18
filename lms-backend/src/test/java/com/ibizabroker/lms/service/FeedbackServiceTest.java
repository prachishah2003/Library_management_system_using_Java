package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.FeedbackRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Feedback;
import com.ibizabroker.lms.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private FeedbackService feedbackService;

    private Users mockUser;
    private Feedback mockFeedback;

    @BeforeEach
    void setUp() {
        // Mock user
        mockUser = new Users();
        mockUser.setUsername("testUser");

        // Mock feedback
        mockFeedback = new Feedback();
        mockFeedback.setUser(mockUser);
        mockFeedback.setFeedbackText("Great library!");

        // Set up SecurityContext to mock authentication
        UserDetails userDetails = new User("testUser", "password", List.of());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSubmitFeedback_Success() {
        // Arrange: User is authenticated
        when(usersRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

        // Act
        Feedback savedFeedback = feedbackService.submitFeedback(mockFeedback);

        // Assert
        assertNotNull(savedFeedback);
        assertEquals("Great library!", savedFeedback.getFeedbackText());
        assertEquals(mockUser, savedFeedback.getUser());

        // Verify interactions
        verify(usersRepository, times(1)).findByUsername("testUser");
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testSubmitFeedback_UserNotAuthenticated() {
        // Arrange: No authentication in the security context
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.submitFeedback(mockFeedback));
        assertEquals("User not authenticated", exception.getMessage());

        verify(usersRepository, never()).findByUsername(any());
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testSubmitFeedback_UserNotFound() {
        // Arrange: User not found in repository
        when(usersRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.submitFeedback(mockFeedback));
        assertEquals("User not found", exception.getMessage());

        verify(usersRepository, times(1)).findByUsername("testUser");
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testGetAllFeedback() {
        // Arrange: Return a list of feedback from repository
        Feedback feedback2 = new Feedback();
        feedback2.setFeedbackText("Nice book collection!");
        List<Feedback> feedbackList = Arrays.asList(mockFeedback, feedback2);
        when(feedbackRepository.findAllByOrderByCreatedAtDesc()).thenReturn(feedbackList);

        // Act
        List<Feedback> result = feedbackService.getAllFeedback();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Great library!", result.get(0).getFeedbackText());
        assertEquals("Nice book collection!", result.get(1).getFeedbackText());

        verify(feedbackRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void testDeleteFeedback_Success() {
        // Arrange: Feedback exists
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(mockFeedback));

        // Act
        feedbackService.deleteFeedback(1L);

        // Assert
        verify(feedbackRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFeedback_NotFound() {
        // Arrange: Feedback not found
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.deleteFeedback(1L));
        assertEquals("Feedback not found", exception.getMessage());

        verify(feedbackRepository, never()).deleteById(any());
    }
}

