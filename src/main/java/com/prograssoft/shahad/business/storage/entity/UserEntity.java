package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name="LOGIN_USER")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name="EMAIL",unique = true, nullable = false)
    private String email;
    @Column(name="PASSWORD")
    private String password;
    @Column(name = "USERNAME",unique = true, nullable = false)
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
