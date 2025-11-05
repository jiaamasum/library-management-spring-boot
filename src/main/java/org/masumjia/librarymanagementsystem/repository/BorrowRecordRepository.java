package org.masumjia.librarymanagementsystem.repository;

import org.masumjia.librarymanagementsystem.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBookIdAndUserIdAndReturnDateIsNull(Long bookId, Long userId);
}
