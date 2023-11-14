package com.korea.article.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Note> noteList = noteService.getList();
        if (noteList.isEmpty()) {
            noteService.createNoteAndArticle("새노트");
        }

        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("articleList", noteList.get(0).getArticleList());
        model.addAttribute("targetArticle", noteList.get(0).getArticleList().get(0));
        return "main";
    }

    @PostMapping("/create")
    public String createNote() {
        noteService.createNoteAndArticle("새노트");
        int size = noteService.getAllNote().size()-1;
        int lastNum = noteService.getAllNote().get(size).getId();
        return "redirect:/note/" + lastNum;
    }

    @GetMapping("/{noteid}")
    public String detail(@PathVariable("noteid") Integer noteid, Model model) {

        Note targetNote = noteService.findById(noteid);

        model.addAttribute("noteList", noteService.getList());
        model.addAttribute("targetNote", targetNote);
        model.addAttribute("articleList", targetNote.getArticleList());
        model.addAttribute("targetArticle", targetNote.getArticleList().get(0));
        return "main";
    }

    @PostMapping("/delete")
    public String deleteNote(Integer targetNoteid) {
        if (noteService.getList().size() != 1) {
            noteService.deleteNote(targetNoteid);
        }
        return "redirect:/";
    }

    @PostMapping("/groupAdd")
    public String groupAdd(Integer targetNoteid) {
        noteService.groupAdd(targetNoteid);
        int size = noteService.getAllNote().size()-1;
        int lastNum = noteService.getAllNote().get(size).getId();
        return "redirect:/note/" + lastNum;
    }
}
