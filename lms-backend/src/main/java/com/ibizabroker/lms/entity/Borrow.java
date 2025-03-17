package com.ibizabroker.lms.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "borrow") // Lowercase table name for PostgreSQL compatibility
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrow_seq")
    @SequenceGenerator(name = "borrow_seq", sequenceName = "borrow_seq", allocationSize = 1)
    private Integer borrowId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
}
