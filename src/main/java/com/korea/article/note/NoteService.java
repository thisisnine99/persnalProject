package com.korea.article.note;

import com.korea.article.article.Article;
import com.korea.article.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final ArticleRepository articleRepository;

    public List<Note> getList() {
        return noteRepository.findByParentId(null);
    }

    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    public Note createNoteAndArticle(String title) {
        Note note = new Note();
        note.setTitle(title);
        note.setCreateDate(LocalDateTime.now());
        noteRepository.save(note);

        Article article = new Article();
        article.setTitle("NewTitle...");
        article.setContent("");
        article.setCreateDate(LocalDateTime.now());
        article.setNote(note);
        articleRepository.save(article);

        return note;
    }

    public Note findById(Integer id) {
        return noteRepository.findById(id).get();
    }

    public void deleteNote(Integer targetNoteid) {
        Note targetNote = findById(targetNoteid);
        noteRepository.delete(targetNote);
    }

    public void groupAdd(Integer targetNoteid) {
        Note targetNote = findById(targetNoteid);
        Note note = createNoteAndArticle("하위노트");
        note.setParent(targetNote);
        noteRepository.save(note);

    }

}
