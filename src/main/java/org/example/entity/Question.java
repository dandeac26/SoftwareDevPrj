package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
import java.sql.Blob;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
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

    //@OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Vote> votes;

    @Column(name="vote_count")
    private int voteCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name="body")
    private String body;

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "question_votes", joinColumns = @JoinColumn(name = "question_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "vote")
    private Map<String, Integer> votes = new HashMap<>();

    public Question(){

    }

    public Question(String author, Long author_id,String title, String body,LocalDateTime date,int voteCount, String picture, List<Tag> tags, Map<String, Integer> votes) {
        this.author = author;
        this.author_id = author_id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.picture = picture;
        this.tags = tags;
        this.votes = votes;
        this.voteCount = voteCount;
    }
//    public int calculateTotalVotes() {
//        int totalVotes = 0;
//        for (Vote vote : this.votes) {
//            totalVotes += vote.isUpvote() ? 1 : -1;
//        }
//        return totalVotes;
//    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Map<String, Integer> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, Integer> votes) {
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
