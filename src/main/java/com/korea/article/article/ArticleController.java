package com.korea.article.article;

import com.korea.article.note.Note;
import com.korea.article.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final NoteService noteService;

    @PostMapping("/create")
    public String createArticle(Integer targetNoteid) {
        Note targetNote = noteService.findById(targetNoteid);
        articleService.createArticle(targetNote);
        int size = articleService.getAllArticle().size() - 1;
        int lastNum = articleService.getAllArticle().get(size).getId();

        return "redirect:/article/" + lastNum;
    }

    @GetMapping("/{targetArticleid}")
    public String detail(@PathVariable("targetArticleid") Integer targetArticleid,
                         Model model) {
        Article targetArticle = articleService.findById(targetArticleid);

        model.addAttribute("noteList", noteService.getList());
        model.addAttribute("targetNote", targetArticle.getNote());
        model.addAttribute("articleList", targetArticle.getNote().getArticleList());
        model.addAttribute("targetArticle", targetArticle);
        return "main";
    }

    @PostMapping("/modify")
    public String modify(Integer targetArticleid, String title, String content) {
        Article targetArticle = articleService.findById(targetArticleid);
        if (title.isBlank()) {
            title = "제목없음";
        }
        articleService.modifyArticle(targetArticle, title, content);
        return "redirect:/article/" + targetArticleid;
    }

    @PostMapping("/delete")
    public String deleteArticle(Integer targetArticleid) {
        int noteid = articleService.findById(targetArticleid).getNote().getId();
        Note targetNote = noteService.findById(noteid);
        if (targetNote.getArticleList().size() != 1) {
            articleService.deleteArticle(targetArticleid);
        }
        return "redirect:/note/" + noteid;
    }
}
