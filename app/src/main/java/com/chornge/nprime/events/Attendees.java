package com.chornge.nprime.events;

import com.chornge.nprime.users.User;

interface Attendees {
    void addGuest(User user);

    void removeGuest(User user);
}
