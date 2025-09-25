package org.masumjia.librarymanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.masumjia.librarymanagementsystem.entity.Book;
import org.masumjia.librarymanagementsystem.entity.BorrowRecord;
import org.masumjia.librarymanagementsystem.entity.User;
import org.masumjia.librarymanagementsystem.repository.BorrowRecordRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BorrowServiceTest {

    @Test
    void borrow_ok() {
        BorrowRecordRepository repo = mock(BorrowRecordRepository.class);
        BookService bookSvc = mock(BookService.class);
        UserService userSvc = mock(UserService.class);

        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        User user = new User();
        user.setId(1L);

        when(bookSvc.getEntity(1L)).thenReturn(book);
        when(userSvc.getEntity(1L)).thenReturn(user);
        when(repo.save(any(BorrowRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        BorrowService svc = new BorrowService(repo, bookSvc, userSvc);

        BorrowRecord rec = svc.borrow(1L, 1L);

        assertNotNull(rec);

        verify(repo, times(1)).save(any(BorrowRecord.class));
    }
}
