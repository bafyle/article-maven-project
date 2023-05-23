package com.vodafone.repo;

import org.springframework.stereotype.Repository;

import com.vodafone.model.Article;

@Repository("readOnlyArticleRepository")
public interface ReadOnlyArticleRepository extends ReadOnlyRepository<Article, Integer>{
    
}
