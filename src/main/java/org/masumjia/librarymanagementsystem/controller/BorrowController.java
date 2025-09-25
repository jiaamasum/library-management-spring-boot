package org.masumjia.librarymanagementsystem.controller;

import org.masumjia.librarymanagementsystem.entity.BorrowRecord;
import org.masumjia.librarymanagementsystem.service.BorrowService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowController {
    private final BorrowService borrowService;
    public BorrowController(BorrowService borrowService) { this.borrowService = borrowService; }

    @PostMapping("/borrow/{bookId}/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowRecord borrow(@PathVariable Long bookId, @PathVariable Long userId){
        return borrowService.borrow(bookId, userId);
    }

    @PostMapping("/return/{bookId}/user/{userId}")
    public BorrowRecord returnBook(@PathVariable Long bookId, @PathVariable Long userId){
        return borrowService.returnBook(bookId, userId);
    }
}
