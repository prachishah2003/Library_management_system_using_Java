package com.ibizabroker.lms.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "requested_books") // Lowercase for PostgreSQL compatibility
public class RequestedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requested_books_seq")
    @SequenceGenerator(name = "requested_books_seq", sequenceName = "requested_books_seq", allocationSize = 1)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "requested_by", nullable = false)
    private Users requestedBy;

    @Column(name = "requested_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedAt;

    @PrePersist
    protected void onCreate() {
        this.requestedAt = new Date();
    }
}
