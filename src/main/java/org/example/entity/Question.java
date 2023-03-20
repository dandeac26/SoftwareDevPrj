package org.example.entity;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
import java.sql.Blob;
import java.sql.Date;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="author")
    private String author;
    @Column(name="title")
    private String title;
    @Column(name="creation_time")
    private Date date;
    @Column(name = "picture")
    private Long picture;

    @ManyToMany
    private List<Tag> tags;
    @Column(name="text")
    private String text;
    public Question(){

    }

    public Question(Long id, String author, DateTimeFormat dateTimeFormat, Long picture) {
        this.id = id;
        this.author = author;
        this.date = (Date) dateTimeFormat;
        this.picture = picture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DateTimeFormat getDateTimeFormat() {
        return (DateTimeFormat) date;
    }

    public void setDateTimeFormat(DateTimeFormat dateTimeFormat) {
        this.date = (Date) dateTimeFormat;
    }

    public Long getPicture() {
        return picture;
    }

    public void setPicture(Long picture) {
        this.picture = picture;
    }
}
