package com.example.easynotes.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.easynotes.model.ESNote;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteESRepository;

@Service
public class elasticSearch implements esInterface {
	@Autowired
	private NoteESRepository noteESRepository;

	public void addToES(Note note) {
		ESNote esnote = new ESNote();
		esnote.setNoteId(note.getId());
		esnote.setTitle(note.getTitle());
		esnote.setContent(note.getContent());
		esnote.setCreatedAt();
		esnote.setUpdatedAt();
		noteESRepository.save(esnote);
	}
	public List<ESNote> getFromES(String text) {
		List<ESNote> res = noteESRepository.findByTitleContainingOrContentContaining(text, text);
		return res;
	}

	public void deleteDocFromES(Long noteId) {
		noteESRepository.deleteByNoteId(noteId);
	}
}
