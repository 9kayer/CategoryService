package com.blog.microservices.controllers;

import com.blog.microservices.dtos.category.CategoryParser;
import com.blog.microservices.dtos.category.CategoryRequest;
import com.blog.microservices.dtos.category.CategoryResponse;
import com.blog.microservices.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${root}")
public class CategoryController {

    @Resource(name = "categoryService")
    private CategoryService service;

    @Resource(name = "categoryParser")
    private CategoryParser categoryParser;

    @GetMapping
    public Mono<ResponseEntity<Set<CategoryResponse>>> getCategories() {
        return service.getCategories()
                        .collectList()
                        .flatMap(list -> Mono.just( ResponseEntity.ok( new HashSet<>(list.stream()
                                                                        .map(category -> categoryParser.toDTO(category))
                                                                        .collect(Collectors.toList())))
                        ));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CategoryResponse>> getCategory(@PathVariable String id){
        return service.getCategoryById(id).map(category -> ResponseEntity.ok(categoryParser.toDTO(category)));
    }

    @PostMapping
    public Mono<ResponseEntity<CategoryResponse>> create(@RequestBody CategoryRequest categoryRequest) {
        return service.create(categoryRequest).map(category -> ResponseEntity.ok(categoryParser.toDTO(category)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable String id){
        return service.deleteById(id).map(item -> ResponseEntity.ok(item));
    }
}
