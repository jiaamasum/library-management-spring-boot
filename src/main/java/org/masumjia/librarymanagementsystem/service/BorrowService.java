package org.masumjia.librarymanagementsystem.service;

import org.masumjia.librarymanagementsystem.entity.Book;
import org.masumjia.librarymanagementsystem.entity.BorrowRecord;
import org.masumjia.librarymanagementsystem.entity.User;
import org.masumjia.librarymanagementsystem.exception.ConflictException;
import org.masumjia.librarymanagementsystem.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;
import org.masumjia.librarymanagementsystem.exception.NotFoundException;

import java.time.LocalDateTime;

@Service
public class BorrowService {
    private final BorrowRecordRepository borrowRepo;
    private final BookService bookService;
    private final UserService userService;

    public BorrowService(BorrowRecordRepository borrowRepo, BookService bookService, UserService userService) {
        this.borrowRepo = borrowRepo;
        this.bookService = bookService;
        this.userService = userService;
    }

    public BorrowRecord borrow(Long bookId, Long userId){
        Book book = bookService.getEntity(bookId);
        User user = userService.getEntity(userId);

        if(Boolean.FALSE.equals(book.getAvailable())){
            throw new ConflictException("Book already borrowed");
        }
        // create record
        BorrowRecord rec = new BorrowRecord();
        rec.setBook(book);
        rec.setUser(user);
        rec.setBorrowDate(LocalDateTime.now());
        book.setAvailable(false);
        return borrowRepo.save(rec);
    }

    public BorrowRecord returnBook(Long bookId, Long userId){
        Book book = bookService.getEntity(bookId);
        userService.getEntity(userId);

        BorrowRecord rec = borrowRepo.findByBookIdAndUserIdAndReturnDateIsNull(bookId, userId)
                .orElseThrow(() -> new NotFoundException("Active borrow record not found"));
        rec.setReturnDate(LocalDateTime.now());
        book.setAvailable(true);
        return borrowRepo.save(rec);
    }
}
