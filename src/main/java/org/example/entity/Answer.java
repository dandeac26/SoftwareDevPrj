package org.example.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="author")
    private Long author;

    @Column(name="author_name")
    private String authorName;
    @Column(name="text")
    private String text;
    @Column(name="creation_time")
    private LocalDateTime creationTime;
    @Column(name = "picture", columnDefinition = "MEDIUMTEXT")
    private String picture;

    @Column(name="votes")
    private Long votes;

    @Column(name="questionId")
    private Long questionId;
    // getters and setters


    public Answer(){

    }

    public Answer(Long id, Long author, String authorName, String text, LocalDateTime creationTime, String picture, Long votes, Long questionId) {
        this.id = id;
        this.author = author;
        this.authorName = authorName;
        this.text = text;
        this.creationTime = creationTime;
        this.picture = picture;
        this.votes = votes;
        this.questionId = questionId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAuthor() {
        return author;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
