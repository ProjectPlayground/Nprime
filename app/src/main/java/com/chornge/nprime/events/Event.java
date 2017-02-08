package com.chornge.nprime.events;

import android.widget.ImageButton;

import com.chornge.nprime.users.User;

import java.util.Calendar;
import java.util.List;

public class Event { //implements AttendeesInterface {
    private String eventName;
    private Calendar eventStart;
    private Calendar eventEnd;
    private String eventDescription = " ";
    private List<Message> messages;
    private List<User> guestList;
    private User eventCreator;
    private String eventType = "public";
    private ImageButton eventLogo;

    public Event() {
        //blank default constructor
    }

    public Event(String eventName, User eventCreator) {
        this.eventName = eventName;
        this.eventCreator = eventCreator;
    }

    public String getEventCreator() {
        return eventCreator.getFullName();
    }

    public void setEventCreator(User eventCreator) {
        this.eventCreator = eventCreator;
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

    public Calendar getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Calendar eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Calendar getEventStart() {
        return eventStart;
    }

    public void setEventStart(Calendar eventStart) {
        this.eventStart = eventStart;
    }

    public List<User> getGuests() {
        return guestList;
    }
}
