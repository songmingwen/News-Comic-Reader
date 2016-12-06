package com.song.sunset.beans;

/**
 * Created by songmw3 on 2016/12/5.
 */

public class Comment {

    private User user;

    private String content;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
