package com.uef.library.service;

import com.uef.library.model.Book;
import com.uef.library.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Page<Book> listAllBooks(String keyword, Long categoryId, String availability, Pageable pageable);
    List<Category> getAllCategories();
    Optional<Book> getBookById(Long id);

}