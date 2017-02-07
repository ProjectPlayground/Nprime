package com.chornge.nprime.events;

import com.chornge.nprime.users.User;

import java.util.List;

interface Attendees {
    void addGuest(User user);

    List<User> getGuests();

    void removeGuest(User user);
}
