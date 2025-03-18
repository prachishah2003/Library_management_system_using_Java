package com.ibizabroker.lms.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "book_ratings")
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_rating_seq")
    @SequenceGenerator(name = "book_rating_seq", sequenceName = "book_rating_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private int rating; // Rating out of 5 stars

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
