package com.chornge.nprime.events;

import android.widget.ImageButton;

import com.chornge.nprime.users.User;

import java.util.Calendar;
import java.util.List;

public class Event {
    protected String name;
    private Calendar date;
    private Calendar time;
    private List<User> usersAttending;
    private boolean isPublicEvent;
    private int eventID;
    private ImageButton imageButton;

    public Event(int eventID, String name, boolean isPublicEvent) {
        this.eventID = eventID;
        this.name = name;
        this.isPublicEvent = isPublicEvent;
    }

    public boolean isPublicEvent() {
        return isPublicEvent;
    }

    public int getEventID() {
        return eventID;
    }

    public List<User> getUsersAttending() {
        return usersAttending;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
