package com.chornge.nprime.users;

import android.widget.ImageButton;

public class User { //implements NetworkingInterface {
    private String fullName;
    private ImageButton userPhoto;
    private String userEmail;
//    private List<Event> userEventList;
//    private List<User> followers;
//    private List<User> peopleUserFollows;

    /**
     * look into ThreadLocal
     */
    public User() {
        //blank default constructor
    }

    public User(String fullName, String userEmail) {
        this.fullName = fullName;
        this.userEmail = userEmail;
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
}
