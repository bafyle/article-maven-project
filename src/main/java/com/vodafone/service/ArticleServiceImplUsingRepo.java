package com.vodafone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vodafone.controller.ArticlesController;
import com.vodafone.controller.AuthorController;
import com.vodafone.errorhandlling.NotFoundException;
import com.vodafone.model.*;
import com.vodafone.repo.ArticleRepo;
import com.vodafone.repo.ReadOnlyArticleRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service("articleServiceImplUsingRepo")
@Transactional
public class ArticleServiceImplUsingRepo implements ArticleService
{
    @Autowired
    ArticleRepo repo;

    @Autowired
    ReadOnlyArticleRepository RORepo;

    @Override
    public List<Article> getAllArticles()
    {
        List<Article> arts =  RORepo.findAll();
        for(var art : arts)
            System.out.println(art);
        for(var article : arts)
        {
            addLinks(article);
        }
        return arts;
    }

    @Override
    public Article getArticleById(Integer id)
    {
        var optionalArticle = RORepo.findById(id);
        if(optionalArticle.isEmpty())
        {
            throw new NotFoundException(String.format("Article by id: %s not found", id));
        }
        var article = optionalArticle.get();
        addLinks(article);
        return article;
    }

    @Override
    public List<Article> getArticlesByAuthorName(String authorName)
    {
        var arts = repo.findByAuthorContains(authorName);
        for(var article : arts)
        {
            addLinks(article);
        }
        return arts;
    }

    @Override
    public Article addArticle(Article article)
    {
        return repo.save(article);
    }

    @Override
    public void deleteArticle(Integer id)
    {
        repo.deleteById(id);
    }

    @Override
    public Article updateArticle(Integer id, Article article)
    {
        article.setId(id);
        return repo.save(article);
    }

    private void addLinks(Article article){
        List<Links> links = new ArrayList<>();
        Links self = new Links();

        Link selfLink = linkTo(methodOn(ArticlesController.class)
                .getArticle(article.getId())).withRel("self");

        self.setRel("self");
        self.setHref(selfLink.getHref());

        Links authorLink = new Links();
        Link authLink = linkTo(methodOn(AuthorController.class)
                .getAuthorById(article.getAuthorId())).withRel("author");
        authorLink.setRel("author");
        authorLink.setHref(authLink.getHref());

        links.add(self);
        links.add(authorLink);
        article.setLinks(links);
    }
}
