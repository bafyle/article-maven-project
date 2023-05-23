package com.vodafone.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.model.Article;
import java.util.List;

@Repository
public interface ArticleRepo extends CrudRepository<Article, Integer>
{
    List<Article> findByAuthor(String author);
    List<Article> findByAuthorContains(String author);
}