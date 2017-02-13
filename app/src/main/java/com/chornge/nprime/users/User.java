package com.chornge.nprime.users;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

public class User implements Parcelable { //implements NetworkingInterface {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String fullName;
    private ImageButton userPhoto;
    private String userEmail;
    private String userID;
    private List<String> userEvents = new LinkedList<>();
    private List<String> followers = new LinkedList<>();
    private List<String> followings = new LinkedList<>();

    /**
     * look into ThreadLocal
     */
    public User() {
        //blank default constructor
    }

    public User(String userID) {
        this.userID = userID;
    }

    public User(String fullName, String userEmail) {
        this.fullName = fullName;
        this.userEmail = userEmail;
    }

    protected User(Parcel in) {
        this.fullName = in.readString();
        this.userEmail = in.readString();
        this.userID = in.readString();
        this.userEvents = in.createStringArrayList();
        this.followers = in.createStringArrayList();
    }

    public List<String> getUserEvents() {
        return userEvents;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getFollowings() {
        return followings;
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
        this.userEvents.add(eventID);
    }

    public void detatchEventFromUser(String eventID) {
        this.userEvents.remove(eventID);
    }

    public void attachFollower(String followerID) {
        this.followers.add(followerID);
    }

    public void detatchFollower(String followerID) {
        this.followers.remove(followerID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //
        parcel.writeString(fullName);
        parcel.writeString(userEmail);
        parcel.writeString(userID);
        parcel.writeStringList(userEvents);
        parcel.writeStringList(followers);
    }
}
