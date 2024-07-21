package com.codegym.baitap_blog.service;

import com.codegym.blogapplication.model.Blog;
import com.codegym.blogapplication.repository.IBlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService implements IBlogService{

    @Autowired
    private IBlogRepo blogRepo;

    @Override
    public Iterable<Blog> findAll() {
        return blogRepo.findAll();
    }

    @Override
    public void save(Blog blog) {
        blogRepo.save(blog);
    }

    @Override
    public Blog findById(Long id) {
        return blogRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        blogRepo.deleteById(id);
    }

    @Override
    public void update(Long id) {

    }
}
