package com.chornge.nprime.events;

import android.widget.ImageButton;

import com.chornge.nprime.users.User;

import java.util.Calendar;
import java.util.List;

public class Event implements Guests {
    private String eventName;
    private Calendar eventDate;
    private Calendar eventTime;
    private String eventDescription = " ";
    private List<User> guests;
    private User eventCreator;
    private List<User> admins;
    private boolean isPublicEvent = true;
    private int eventID;
    private ImageButton eventLogo;

    public Event(int eventID, String eventName, User eventCreator) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventCreator = eventCreator;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public String getEventCreator() {
        return eventCreator.getUserName();
    }

    public void setAdmin(User newAdmin) {
        admins.add(newAdmin);
    }

    public boolean isPublicEvent() {
        return isPublicEvent;
    }

    public void setPublicEvent(boolean publicEvent) {
        isPublicEvent = publicEvent;
    }

    public int getEventID() {
        return eventID;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String newEventDescription) {
        this.eventDescription = newEventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String newEventName) {
        this.eventName = newEventName;
    }

    public ImageButton getEventLogo() {
        return eventLogo;
    }

    public void setEventLogo(ImageButton newEventLogo) {
        this.eventLogo = newEventLogo;
    }

    public Calendar getEventTime() {
        return eventTime;
    }

    public void setEventTime(Calendar newEventTime) {
        this.eventTime = newEventTime;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar newEventDate) {
        this.eventDate = newEventDate;
    }

    @Override
    public void addGuest(User newGuest) {
        guests.add(newGuest);
    }

    @Override
    public void removeGuest(User guestToRemove) {
        guests.remove(guestToRemove);
    }

    @Override
    public List<User> getGuests() {
        return guests;
    }
}
