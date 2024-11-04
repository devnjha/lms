package com.lld.lms.controller;

import com.lld.lms.model.BorrowingRecord;
import com.lld.lms.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @GetMapping
    public List<BorrowingRecord> getAllRecords() {
        return borrowingRecordService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecord> getRecordById(@PathVariable Long id) {
        BorrowingRecord record = borrowingRecordService.findById(id);
        return (record != null) ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public BorrowingRecord createRecord(@RequestBody BorrowingRecord record) {
        return borrowingRecordService.save(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowingRecord> updateRecord(@PathVariable Long id, @RequestBody BorrowingRecord recordDetails) {
        BorrowingRecord updatedRecord = borrowingRecordService.update(id, recordDetails);
        return (updatedRecord != null) ? ResponseEntity.ok(updatedRecord) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        borrowingRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
