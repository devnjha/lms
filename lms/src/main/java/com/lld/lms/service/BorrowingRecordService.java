package com.lld.lms.service;

import com.lld.lms.model.Book;
import com.lld.lms.model.BorrowingRecord;
import com.lld.lms.model.Member;
import com.lld.lms.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    public List<BorrowingRecord> findAll() {
        return borrowingRecordRepository.findAll();
    }

    public BorrowingRecord findById(Long id) {
        Optional<BorrowingRecord> record = borrowingRecordRepository.findById(id);
        return record.orElse(null);
    }

    public BorrowingRecord save(BorrowingRecord record) {
        return borrowingRecordRepository.save(record);
    }

    public BorrowingRecord update(Long id, BorrowingRecord recordDetails) {
        BorrowingRecord record = findById(id);
        if (record != null) {
            record.setBook(recordDetails.getBook());
            record.setMember(recordDetails.getMember());
            record.setBorrowDate(recordDetails.getBorrowDate());
            record.setDueDate(recordDetails.getDueDate());
            record.setReturnDate(recordDetails.getReturnDate());
            record.setLateFee(recordDetails.getLateFee());
            return borrowingRecordRepository.save(record);
        }
        return null;
    }

    public void delete(Long id) {
        borrowingRecordRepository.deleteById(id);
    }

    public List<BorrowingRecord> findByMemberId(Long memberId) {
        return borrowingRecordRepository.findByMemberId(memberId);
    }

    public Optional<BorrowingRecord> findByBookAndMember(Book book, Member member) {
        return borrowingRecordRepository.findByMemberIdAndBookId(member.getId(), book.getId());
    }
}
