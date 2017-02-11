package com.chornge.nprime.events;

import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

public class Event { //implements AttendeesInterface {
    private String eventName;
    private String eventStartDate;
    private String eventEndDate;
    private String eventStartTime;
    private String eventEndTime;
    private String eventDescription = " ";
    private List<Message> messages = new LinkedList<>();
    private List<String> guestList = new LinkedList<>();
    private String eventCreatorID;
    private String eventID;
    private String eventType;
    private ImageButton eventLogo;

    public Event() {
        //blank default constructor
    }

    public Event(String eventCreatorID) {
        this.eventCreatorID = eventCreatorID;
        guestList.add(eventCreatorID);
    }

    public void setEventCreatorID(String eventCreatorID) {
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

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
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

    public List<String> getGuests() {
        return guestList;
    }
}
