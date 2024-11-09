package com.lld.lms.controller;

import com.lld.lms.model.BorrowingRecord;
import com.lld.lms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-service")
public class LibraryServiceController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/books/{bookId}/members/{memberId}/borrow")
    public ResponseEntity<String> borrowBookById(@PathVariable Long bookId, @PathVariable Long memberId) throws InterruptedException {
        boolean result = libraryService.borrowBookById(bookId, memberId);
        if (result) {
            return ResponseEntity.ok("Book borrowed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to borrow book. Either the book or member was not found, or the book is not available.");
        }
    }

    @GetMapping("/members/{id}/borrowings")
    public ResponseEntity<List<BorrowingRecord>> getBorrowingRecordsByMemberId(@PathVariable Long id) {
        List<BorrowingRecord> records = libraryService.findBorrowingRecordsByMemberId(id);
        if (records.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(records);
        }
    }

    @PostMapping("/books/{bookId}/members/{memberId}/return")
    public ResponseEntity<String> returnBookById(@PathVariable Long memberId, @PathVariable Long bookId) {
        try {
            libraryService.returnBookById(bookId, memberId);
            return ResponseEntity.ok("Book returned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error returning book: " + e.getMessage());
        }
    }
}
