package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="text")
    private String text;
    @Column(name="creation_time")
    private Date creationTime;
    @Column(name="picture")
    private Long picture;
    public Answer(){

    }
    public Answer(Long id, String text, Date creationTime, Long picture) {
        this.id = id;
        this.text = text;
        this.creationTime = creationTime;
        this.picture = picture;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Long getPicture() {
        return picture;
    }

    public void setPicture(Long picture) {
        this.picture = picture;
    }
}
