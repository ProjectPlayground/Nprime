package com.chornge.nprime.users;

import android.widget.ImageButton;

import com.chornge.nprime.events.Event;

import java.util.List;

public class User implements Networking {
    private String userName;
    private String userID;
    private ImageButton userPhoto;
    private String userEmail;
    private List<Event> userEventList;
    private List<User> followers;
    private List<User> peopleUserFollows;

    public User() {
        //blank default constructor required for firebase
    }

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

    @Override
    public void addFollower(User user) {
        followers.add(user);
    }

    @Override
    public void removeFollower(User user) {
        followers.remove(user);
    }

    @Override
    public void startFollowing(User user) {
        peopleUserFollows.add(user);
    }

    @Override
    public void stopFollowing(User user) {
        peopleUserFollows.remove(user);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getPeopleUserFollows() {
        return peopleUserFollows;
    }
}
