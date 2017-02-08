package com.chornge.nprime.events;

import android.widget.ImageButton;

import com.chornge.nprime.users.User;

import java.util.Calendar;
import java.util.List;

public class Event { //implements AttendeesInterface {
    private String eventName;
    private Calendar eventDate;
    private Calendar eventTime;
    private String eventDescription = " ";
    private List<Message> messages;
    private List<User> guestList;
    private User eventCreator;
    private String eventType = "public";
    private ImageButton eventLogo;

    public Event(String eventName, User eventCreator) {
        this.eventName = eventName;
        this.eventCreator = eventCreator;
    }

    public String getEventCreator() {
        return eventCreator.getFullName();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String newEventDescription) {
        this.eventDescription = newEventDescription;
    }

    public List<Message> getMessages() {
        return messages;
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

    public List<User> getGuests() {
        return guestList;
    }
}
