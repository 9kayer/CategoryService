package com.blog.microservices.repositories;

import com.blog.microservices.domains.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryRepository extends ReactiveCrudRepository<Category, String> {
}
