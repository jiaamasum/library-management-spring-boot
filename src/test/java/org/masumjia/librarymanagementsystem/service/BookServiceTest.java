package org.masumjia.librarymanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.masumjia.librarymanagementsystem.dto.CreateBookRequest;
import org.masumjia.librarymanagementsystem.entity.Book;
import org.masumjia.librarymanagementsystem.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Test
    void createBook_ok() {
        // Arrange
        BookRepository repo = mock(BookRepository.class);

        // IMPORTANT: match app config so Book -> BookDto maps public fields
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE);

        BookService svc = new BookService(repo, mm);

        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("Test");
        req.setAuthor("Author");
        req.setIsbn("X-1");

        when(repo.save(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            b.setId(10L);
            return b;
        });

        // Act
        var dto = svc.create(req);

        // Assert
        assertEquals("Test", dto.title);
        assertEquals("Author", dto.author);
        assertEquals("X-1", dto.isbn);
        assertTrue(dto.available);
        assertEquals(10L, dto.id);
    }
}
