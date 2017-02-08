package com.chornge.nprime.users;

import com.chornge.nprime.events.Event;

interface NetworkingInterface {
    void addFollower(User user);

    void removeFollower(User user);

    void startFollowing(User user);

    void stopFollowing(User user);

    void addEvent(Event event);

    void removeEvent(Event event);

    void createEvent(Event event, User eventCreator);

    void deleteEvent(Event event);
}
