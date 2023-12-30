package com.example.easynotes.utils;

import com.example.easynotes.model.Note;

import java.util.List;

import com.example.easynotes.model.ESNote;


public interface esInterface {

	void addToES(Note note);
	List<ESNote> getFromES(String text);
	void deleteDocFromES(Long noteId);
} 
