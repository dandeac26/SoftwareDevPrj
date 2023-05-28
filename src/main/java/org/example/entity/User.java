package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name="cnp")
    private Long cnp;


    @Column(name = "l_name")
    private String lastName;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "password")
    private String password;



    public User() {

    }

    public User(Long userId, Long cnp, String lastName, String firstName, String email, String password) {
        this.userId = userId;
        this.cnp = cnp;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public Long getCnp() {
        return cnp;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

