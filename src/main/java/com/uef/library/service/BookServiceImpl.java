package com.uef.library.service;

import com.uef.library.config.BookSpecification;
import com.uef.library.model.Book;
import com.uef.library.model.Category;
import com.uef.library.repository.BookRepository;
import com.uef.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Book> listAllBooks(String keyword, Long categoryId, String availability, Pageable pageable) {
        // Ta sẽ truyền availability vào Specification
        Specification<Book> spec = BookSpecification.filterBy(keyword, categoryId, availability);
        return bookRepository.findAll(spec, pageable);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
}