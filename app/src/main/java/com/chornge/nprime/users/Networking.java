package com.chornge.nprime.users;

import com.chornge.nprime.events.Event;

interface Networking {
    void addFollower(User user);

    void removeFollower(User user);

    void startFollowing(User user);

    void stopFollowing(User user);

    void addEvent(Event event);

    void removeEvent(Event event);

    void createEvent(Event event);
}
