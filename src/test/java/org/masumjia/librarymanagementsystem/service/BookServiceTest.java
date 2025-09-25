package org.masumjia.librarymanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.masumjia.librarymanagementsystem.dto.CreateBookRequest;
import org.masumjia.librarymanagementsystem.entity.Book;
import org.masumjia.librarymanagementsystem.repository.BookRepository;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    @Test
    void createBook_ok() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        ModelMapper mm = new ModelMapper();
        BookService svc = new BookService(repo, mm);

        CreateBookRequest req = new CreateBookRequest();
        req.title = "Test";
        req.author = "Author";
        req.isbn = "X-1";

        when(repo.save(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            b.setId(10L);
            return b;
        });

        var dto = svc.create(req);
        assertEquals("Test", dto.title);
        assertEquals("Author", dto.author);
        assertEquals("X-1", dto.isbn);
        assertTrue(dto.available);
    }
}
