package com.example.easynotes.repository;

import com.example.easynotes.model.ESNote;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteESRepository extends ElasticsearchRepository<ESNote, Long> {

	List<ESNote> findByTitleContainingOrContentContaining(String title, String content);
	void deleteByNoteId(Long noteId);
	List<ESNote> findByTitleContainingOrContentContainingAndNoteIdIsNotNull(String title, String content);
}


