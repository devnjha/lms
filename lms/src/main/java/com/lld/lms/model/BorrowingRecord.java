package com.lld.lms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="borrowing_records")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // Reference to the borrowed book

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // Reference to the member who borrowed the book

    private LocalDate borrowDate; // Date when the book was borrowed
    private LocalDate dueDate; // Due date for returning the book
    private LocalDate returnDate; // Date when the book was returned
    private double lateFee; // Fee for late return

}
