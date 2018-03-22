package com.blog.microservices.services;

import com.blog.microservices.domains.Category;
import com.blog.microservices.dtos.category.CategoryParser;
import com.blog.microservices.dtos.category.CategoryRequest;
import com.blog.microservices.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.annotation.Resource;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Resource(name = "categoryParser")
    private CategoryParser parser;

    public Flux<Category> getCategories() {
        return repo.findAll();
    }

    public Mono<Category> getCategoryById(String id) {
        return repo.findById(id);
    }

    public Mono<Category> create(CategoryRequest category) {
        return repo.save(parser.toModel(category));
    }

    public Mono<Void> deleteById(String id) {
        return repo.deleteById(id);
    }
}
