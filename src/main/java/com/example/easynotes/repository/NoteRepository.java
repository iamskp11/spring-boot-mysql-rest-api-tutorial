package com.example.easynotes.repository;

import com.example.easynotes.model.Note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Query("select n from Note n where n.title like %?1% or n.content like %?1%")
	List<Note> getNoteBySearchString(String query);

}
