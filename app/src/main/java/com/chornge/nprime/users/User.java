package com.chornge.nprime.users;

import android.widget.ImageButton;

import com.chornge.nprime.events.Event;

import java.util.List;

public class User {
    private String userName;
    private String userID;
    private ImageButton userPhoto;
    private String userEmail;
    private List<User> everyoneUserIsFollowing;
    private List<User> everyoneFollowingUser;
    private List<Event> userEventList;

    public User(String userID, String userEmail) {
        this.userName = "";
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public ImageButton getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(ImageButton newUserPhoto) {
        this.userPhoto = newUserPhoto;
    }

    // ...look into ThreadLocal...

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }
}
