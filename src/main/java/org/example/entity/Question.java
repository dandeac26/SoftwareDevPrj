package org.example.entity;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
import java.sql.Blob;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="author")
    private String author;

    @Column(name="author_id")
    private Long author_id;
    @Column(name="title")
    private String title;
    @Column(name="creation_time")
    private LocalDateTime date;
    @Column(name = "picture", columnDefinition = "MEDIUMTEXT")
    private String picture;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name="body")
    private String body;


    @Column(name="votes")
    private Long votes;



    public Question(){

    }

    public Question(String author, Long author_id,String title, String body,LocalDateTime date, String picture, List<Tag> tags, Long votes) {
        this.author = author;
        this.author_id = author_id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.picture = picture;
        this.tags = tags;
        this.votes = votes;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getTags() {
        if (tags != null) {
            return tags.stream().map(Tag::getName).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public List<Tag> getTagsObj(){
        return tags;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
