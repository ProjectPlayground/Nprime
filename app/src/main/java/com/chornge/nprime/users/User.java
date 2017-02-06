package com.chornge.nprime.users;

import android.widget.ImageButton;

import java.util.List;

public class User {
    protected String name;
    private String userID;
    private ImageButton imageButton;
    private String email;

    public User(String userID, String email) {
        this.name = "";
        this.userID = userID;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    // ...look into ThreadLocal...

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> everyoneFollowingUser() {
        return null;
    }

    public List<User> everyoneUserIsFollowing() {
        return null;
    }
}
