package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.ESNote;
import com.example.easynotes.model.Note;
import com.example.easynotes.model.SearchNote;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.utils.esInterface;
import com.example.easynotes.utils.textUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    esInterface es;

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        // elasticSearch es = new elasticSearch();
        Note res = noteRepository.save(note);
        es.addToES(res);
        return res;
    }

    @GetMapping("/notes/search")
    public List<Note> searchNote(@Valid @RequestBody SearchNote searchNote) {
        String text = searchNote.getText();
        List<String> splitTexts = textUtils.splitString(text);

        Integer limit = searchNote.getLimit();
        // System.out.println(splitTexts);
        // System.out.println(splitTexts.get(0));
        Set<Note> resultantNotes = new HashSet<Note>();
        for(int i=0; i<splitTexts.size(); i++) {
            List<Note> currentNotes = noteRepository.getNoteBySearchString(splitTexts.get(i));
            for(int j=0;j<currentNotes.size(); j++) {
                resultantNotes.add(currentNotes.get(j));
            }
        }
        List<Note> resultantListNotes = new ArrayList<Note> (resultantNotes);
        List<Note> finalNotesWithLimit = new ArrayList<Note> ();
        for(int i=0;i<Integer.min(limit, resultantListNotes.size()) ;i++) finalNotesWithLimit.add(resultantListNotes.get(i));
        return finalNotesWithLimit;
    }

    @GetMapping("/notes/search2")
    public List<Note> searchNoteES(@Valid @RequestBody SearchNote searchNote) {
        String text = searchNote.getText();
        List<ESNote> res =  es.getFromES(text);
        List<Note> ans = new ArrayList<Note>();
        for(int i=0;i<res.size();i++){
            Note temp = new Note();
            temp.setId(res.get(i).getNoteId());
            temp.setTitle(res.get(i).getTitle());
            temp.setContent(res.get(i).getContent());
            temp.setCreatedAt(res.get(i).getCreatedAt());
            temp.setUpdatedAt(res.get(i).getUpdatedAt());
            ans.add(temp);
        }
        return ans;
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody Note noteDetails) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        try{
            es.deleteDocFromES(note.getId());
        } catch (Exception e) {
            System.out.println("Some error, probably Document missing");
        }
        Note updatedNote = noteRepository.save(note);
        es.addToES(updatedNote);
        return updatedNote;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);
        try{
            es.deleteDocFromES(note.getId());
        } catch (Exception e ) {
            System.out.println("Some error, probably Document missing");
        }

        return ResponseEntity.ok().build();
    }

}
