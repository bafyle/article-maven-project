package com.vodafone.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.model.Author;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Integer>
{
    
}
