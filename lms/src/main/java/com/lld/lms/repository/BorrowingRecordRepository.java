package com.lld.lms.repository;

import com.lld.lms.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByMemberId(Long memberId);
    Optional<BorrowingRecord> findByMemberIdAndBookId(Long memberId,
                                                      Long bookId);
}
