package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private float rate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="others_id")
    private User others;

    public Comment(){}
    public Comment(String content,float rate) {
        this.rate = rate;
        this.content = content;
    }

    public float getRate() {
        return rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOthers() {
        return others;
    }

    public void setOthers(User others) {
        this.others = others;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getRateString(){
        return Float.toString(rate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
