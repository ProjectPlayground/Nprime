package com.chornge.nprime.users;

import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

public class User { //implements NetworkingInterface {
    private String fullName;
    private ImageButton userPhoto;
    private String userEmail;
    private String userID;
    private List<String> userEvents = new LinkedList<>();
    private List<String> followers = new LinkedList<>();
    //private List<String> peopleUserFollows = new LinkedList<>();

    /**
     * look into ThreadLocal
     */
    public User() {
        //blank default constructor
        this.userPhoto = null;
    }

    public User(String userID) {
        this.userID = userID;
    }

    public User(String fullName, String userEmail) {
        this.fullName = fullName;
        this.userEmail = userEmail;
    }

    public List<String> getUserEvents() {
        return userEvents;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ImageButton getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(ImageButton newUserPhoto) {
        this.userPhoto = newUserPhoto;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String newUserFullName) {
        this.fullName = newUserFullName;
    }

    public void attachEventToUser(String eventID) {
        //List<String> eventList = new LinkedList<>();
        //eventList.add(eventID);
        //this.userEvents = eventList;
        this.userEvents.add(eventID);
    }

    public void detatchEventFromUser(String eventID) {
        this.userEvents.remove(eventID);
    }

    public void attachFollower(String followerID) {
        //List<String> follower = new LinkedList<>();
        //follower.add(followerID);
        //this.followers = follower;
        this.followers.add(followerID);
    }

    public void detatchFollower(String followerID) {
        this.followers.remove(followerID);
    }
}
