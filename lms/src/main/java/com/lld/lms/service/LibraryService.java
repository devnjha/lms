package com.lld.lms.service;

import com.lld.lms.model.Book;
import com.lld.lms.model.BorrowingRecord;
import com.lld.lms.model.Member;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Transactional
    public boolean borrowBookById(Long bookId, Long memberId) {

        Member member = memberService.findById(memberId);
        Book book = bookService.findById(bookId);

        if (member == null || book == null) throw new RuntimeException(
                "Member/Book Not found");

        if(book.getAvailableCopies()<=0) throw new RuntimeException("Book is not available");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setMember(member);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setDueDate(LocalDate.now().plusDays(30));
        borrowingRecordService.save(borrowingRecord);

        book.setAvailableCopies(book.getAvailableCopies()-1);
        bookService.update(bookId, book);

        return true;
    }

    public List<BorrowingRecord> findBorrowingRecordsByMemberId(Long memberId) {
        return borrowingRecordService.findByMemberId(memberId);
    }

    public void returnBookById(Long bookId, Long memberId) {
        Member member = memberService.findById(memberId);
        if(member==null) throw new RuntimeException("Member not found");

        Book book = bookService.findById(bookId);
        if(book==null) throw new RuntimeException("Book not found");

        BorrowingRecord borrowingRecord = borrowingRecordService.findByBookAndMember(book, member)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordService.save(borrowingRecord);

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.update(book.getId(),book);
    }
}
