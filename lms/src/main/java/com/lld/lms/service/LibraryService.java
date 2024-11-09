package com.lld.lms.service;

import com.lld.lms.model.Book;
import com.lld.lms.model.BorrowingRecord;
import com.lld.lms.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class LibraryService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BorrowingRecordService borrowingRecordService;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

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

        // this after will also not give correct output unless we refresh or
        // explicitly load the entity
        // logger.info("Before Update due to cascading all :"+ member.getBorrowingRecords());
        // borrowingRecordService.save(borrowingRecord);
        // logger.info("After Update due to cascading all :"+ member.getBorrowingRecords());

        // entityManager.flush();
        // entityManager.refresh(member);
        // memberService.findById(memberId);
        // logger.info("Before Update due to cascading all :"+ member.getBorrowingRecords());
        // borrowingRecordService.save(borrowingRecord);
        // Thread.sleep(5000);
        // logger.info("After Update due to cascading all :"+ member.getBorrowingRecords());

        borrowingRecordService.save(borrowingRecord);
        // member.getBorrowingRecords().add(borrowingRecord);

        book.setAvailableCopies(book.getAvailableCopies()-1);
        bookService.update(bookId, book);
        // logger.info("Before Update due to cascading all :"+ member.getBorrowingRecords());

        return true;
    }

    public List<BorrowingRecord> findBorrowingRecordsByMemberId(Long memberId) {
        Member member = memberService.findById(memberId);
        logger.info("Inside find borroowing records :"+ member.getBorrowingRecords());
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
