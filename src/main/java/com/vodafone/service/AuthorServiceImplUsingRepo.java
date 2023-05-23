package com.vodafone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vodafone.model.Author;
import com.vodafone.repo.AuthorRepo;

@Service("authorServiceImplUsingRepo")
@Transactional
public class AuthorServiceImplUsingRepo implements AuthorService
{
    @Autowired    
    AuthorRepo repo;
    
    @Override
    public Author getAuthorById(Integer id) 
    {
        return repo.findById(id).get();
    }
}
