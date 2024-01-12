package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    esInterface es;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @CrossOrigin(origins = "*")
    @GetMapping("/notes")
    public List<Note> getAllNotes(@RequestParam(defaultValue="10", name="limit") String limit, 
    @RequestParam(defaultValue="0", name="offset") String offset) {
        List<Note> notes =  noteRepository.findAll();
        List<Note> required_notes = new ArrayList<Note>();
        int I_limit = Integer.parseInt(limit);
        int I_offset = Integer.parseInt(offset);

        for(int i=I_offset; i<I_offset+I_limit && i<notes.size(); i++) {
            required_notes.add(notes.get(i));
        }
        return required_notes;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note) {
        Note res = noteRepository.save(note);
        es.addToES(res);
        return res;
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/notes/search")
    public List<Note> searchNote(@Valid @RequestBody SearchNote searchNote) {
        String text = searchNote.getText();
        List<String> splitTexts = textUtils.splitString(text);

        Integer limit = searchNote.getLimit();
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

    @CrossOrigin(origins = "*")
    @GetMapping("/notes/searches")
    public List<Note> searchNoteES(@Valid @RequestBody SearchNote searchNote) {
        String text = searchNote.getText();
        List<String> splitTexts = textUtils.splitString(text);
        Integer limit = searchNote.getLimit();
        Integer offset = searchNote.getOffset();
        List<Long> finalNoteIds =  es.getAllUniqueDocNoteIds(splitTexts);
        List<Note> ans = new ArrayList<Note>();
        for(int i=offset;i<finalNoteIds.size();i++){
            if(ans.size() == limit) break;
            Long noteId = finalNoteIds.get(i);
            try{
                Note curr = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
                ans.add(curr);
            }
            catch (Exception e) {
                logger.error("Exception occured while finding Note by id" + e.getMessage());
                continue;
            }
        }
        return ans;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @CrossOrigin(origins = "*")
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
            logger.error("Exception occured while finding Note by id" + e.getMessage());
        }
        Note updatedNote = noteRepository.save(note);
        es.addToES(updatedNote);
        return updatedNote;
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);
        try{
            es.deleteDocFromES(note.getId());
        } catch (Exception e ) {
            logger.error("Some error, probably Document missing" + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

}
