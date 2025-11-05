package org.masumjia.librarymanagementsystem.service;

import org.masumjia.librarymanagementsystem.dto.BookDto;
import org.masumjia.librarymanagementsystem.dto.CreateBookRequest;
import org.masumjia.librarymanagementsystem.dto.UpdateBookRequest;
import org.masumjia.librarymanagementsystem.entity.Book;
import org.masumjia.librarymanagementsystem.exception.NotFoundException;
import org.masumjia.librarymanagementsystem.repository.BookRepository;
import org.masumjia.librarymanagementsystem.util.PageResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookService(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public BookDto create(CreateBookRequest req){
        Book b = new Book();
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setIsbn(req.getIsbn());
        b.setAvailable(true);
        return mapper.map(bookRepository.save(b), BookDto.class);
    }



    public PageResponse<BookDto> list(String q, int page, int size){
        Pageable pb = PageRequest.of(page, size);
        Page<Book> p;
        if(q == null || q.isBlank()){
            p = bookRepository.findAll(pb);
        } else {
            p = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q, pb);
        }
        return new PageResponse<>(
                p.getContent().stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList()),
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isFirst(), p.isLast()
        );
    }

    public Book getEntity(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public BookDto get(Long id){
        return mapper.map(getEntity(id), BookDto.class);
    }

    public BookDto update(Long id, UpdateBookRequest req){
        Book b = getEntity(id);
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setIsbn(req.getIsbn());
        return mapper.map(bookRepository.save(b), BookDto.class);
    }

    public void delete(Long id){
        Book b = getEntity(id);
        bookRepository.delete(b);
    }
}
