package com.example.easynotes.model;

// import java.text.SimpleDateFormat;
import java.util.Date;

// import javax.validation.constraints.NotBlank;

// import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;

// import javax.persistence.*;

@Document(indexName = "notes", shards = 1, replicas = 0, refreshInterval = "-1")
public class ESNote {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
	private Long noteId;
	// @NotBlank
    private String title;
	// @NotBlank
    private String content;
    private Date createdAt;
    private Date updatedAt;
    
	// public ESNote(Note note) {
	// 	this.setId(note.getId());
	// 	this.setTitle(note.getTitle());
	// 	this.setContent(note.getContent());
	// 	// this.setCreatedAt(note.getCreatedAt());
	// 	// this.setUpdatedAt(note.getUpdatedAt());
	// }
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long NoteId) {
        this.noteId = NoteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
		Date currentDate = new Date();
		this.createdAt =  currentDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
		Date currentDate = new Date();
        this.updatedAt = currentDate;
    }
}