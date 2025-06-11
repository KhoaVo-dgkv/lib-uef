package com.uef.library.config;

import com.uef.library.model.Book;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> filterBy(String keyword, Long categoryId, String availability) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);

            if (StringUtils.hasText(keyword)) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), likePattern),
                        cb.like(cb.lower(root.get("author")), likePattern) // Tìm theo tên tác giả dạng String
                ));
            }

            if (categoryId != null && categoryId > 0) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (StringUtils.hasText(availability)) {
                if ("available".equalsIgnoreCase(availability)) {
                    // Lọc những sách có availableCopies > 0
                    predicates.add(cb.greaterThan(root.get("availableCopies"), 0));
                } else if ("borrowed".equalsIgnoreCase(availability)) {
                    // Lọc những sách có availableCopies = 0
                    predicates.add(cb.equal(root.get("availableCopies"), 0));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}