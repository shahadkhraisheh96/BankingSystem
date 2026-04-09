package com.prograssoft.shahad.business.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotNull;

@JsonDeserialize(builder = User.Builder.class)
public class User {
    private String id;
    @NotNull
    private String email;
    @NotNull
    private String Password;
    @NotNull
    private String userName;

    public User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        Password = builder.password;
        this.userName = builder.userName;
    }


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
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder{
        private String id;
        private String email;
        private String password;
        private String userName;

        protected Builder(){

        }
        public Builder id(String id){
            this.id=id;
            return this;

        }
        public Builder email(String email){
            this.email=email;
            return this;
        }
        public Builder password(String password){
            this.password=password;
            return this;

        }
        public Builder userName(String userName){
            this.userName=userName;
            return this;

        }
        public User build() {
            return new User(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "id='" + id + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }
}
