package com.chornge.nprime.users;

import android.widget.ImageButton;
import android.widget.ListAdapter;

import com.chornge.nprime.events.Event;

import java.util.List;

public class User {
    protected String name;
    protected ListAdapter listAdapter;
    private List<User> everyoneUserIsFollowing;
    private List<User> everyoneFollowingUser;
    private int userID;
    private List<Event> userEvents;
    private ImageButton imageButton;
    private String email;

    public User(int userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public List<User> getEveryoneFollowingUser() {
        return everyoneFollowingUser;
    }

    public List<User> getEveryoneUserIsFollowing() {
        return everyoneUserIsFollowing;
    }

    // ...look into ThreadLocal...

    public List<Event> getUserEvents() {
        return userEvents;
    }

    //userEventsAdapter

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
}
