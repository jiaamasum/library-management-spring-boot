package org.masumjia.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.masumjia.librarymanagementsystem.dto.BookDto;
import org.masumjia.librarymanagementsystem.dto.CreateBookRequest;
import org.masumjia.librarymanagementsystem.dto.UpdateBookRequest;
import org.masumjia.librarymanagementsystem.service.BookService;
import org.masumjia.librarymanagementsystem.util.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@Valid @RequestBody CreateBookRequest req){ return bookService.create(req); }

    @GetMapping
    public PageResponse<BookDto> list(@RequestParam(value="q", required=false) String q,
                                      @RequestParam(defaultValue="0") int page,
                                      @RequestParam(defaultValue="10") int size){
        return bookService.list(q, page, size);
    }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable Long id){ return bookService.get(id); }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest req){
        return bookService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){ bookService.delete(id); }
}
