package com.chornge.nprime.events;

import android.widget.ImageButton;

import com.chornge.nprime.users.User;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Event { //implements AttendeesInterface {
    private String eventName;
    private Calendar eventStart;
    private Calendar eventEnd;
    private String eventDescription = " ";
    private List<Message> messages = new LinkedList<>();
    private List<User> guestList = new LinkedList<>();
    private String eventCreatorID;
    private String eventID;
    private String eventType;
    private ImageButton eventLogo;

    public Event() {
        //blank default constructor
    }

    public Event(String eventCreatorID) {
        this.eventCreatorID = eventCreatorID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventCreator() {
        return eventCreatorID;
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
