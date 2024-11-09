package com.lld.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference("bookReference")
    private Book book; // Reference to the borrowed book

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference("memberReference")
    private Member member; // Reference to the member who borrowed the book

    private LocalDate borrowDate; // Date when the book was borrowed
    private LocalDate dueDate; // Due date for returning the book
    private LocalDate returnDate; // Date when the book was returned
    private double lateFee; // Fee for late return

    @Override
    public String toString()
    {
        String borrowingRecord = "";
        borrowingRecord += "BookId: " + book.getId() + "\n";
        borrowingRecord +="MemberID: " + member.getId() + "\n";
        borrowingRecord += "Date: " + borrowDate + "\n";
        borrowingRecord += "DueDate: " + dueDate + "\n";
        borrowingRecord += "ReturnDate: " + returnDate + "\n";
        borrowingRecord += "LateFee: " + lateFee + "\n";
        return borrowingRecord;
    }

}
