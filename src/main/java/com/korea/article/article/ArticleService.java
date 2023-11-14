package com.korea.article.article;

import com.korea.article.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void createArticle(Note targetNote) {
        Article article = new Article();
        article.setTitle("NewTitle...");
        article.setContent("");
        article.setCreateDate(LocalDateTime.now());
        article.setNote(targetNote);
        articleRepository.save(article);
    }

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public Article findById(Integer targetArticleid) {
        return articleRepository.findById(targetArticleid).get();
    }

    public void modifyArticle(Article targetArticle, String title, String content) {
        targetArticle.setTitle(title);
        targetArticle.setContent(content);
        targetArticle.setModifyDate(LocalDateTime.now());
        articleRepository.save(targetArticle);
    }

    public void deleteArticle(Integer targetArticleid) {
        Article targetArticle = findById(targetArticleid);
        articleRepository.delete(targetArticle);
    }
}
