package com.example.easynotes.model;

// import java.util.Date;

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
	// @NotBlank
    private String title;
	// @NotBlank
    private String content;
    // @Column(nullable = false, updatable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    // @CreatedDate
    // private Date createdAt;
    // @Column(nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    // @LastModifiedDate
    // private Date updatedAt;
    
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

    // public Date getCreatedAt() {
    //     return createdAt;
    // }

    // public void setCreatedAt(Date createdAt) {
    //     this.createdAt = createdAt;
    // }

    // public Date getUpdatedAt() {
    //     return updatedAt;
    // }

    // public void setUpdatedAt(Date updatedAt) {
    //     this.updatedAt = updatedAt;
    // }
}