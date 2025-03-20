package com.ibizabroker.lms.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "books") // Table name in lowercase for PostgreSQL compatibility
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq")
    @SequenceGenerator(name = "books_seq", sequenceName = "books_seq", allocationSize = 1)
    private Integer bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_author", nullable = false)
    private String bookAuthor;

    @Column(name = "book_genre")
    private String bookGenre;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "no_of_copies", nullable = false)
    private Integer noOfCopies;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;



    public void borrowBook() {
        if (this.noOfCopies > 0) {
            this.noOfCopies--;
        }
    }

    public void returnBook() {
        this.noOfCopies++;
    }
}
