package com.chornge.nprime.events;

import com.chornge.nprime.users.User;

interface AttendeesInterface {
    void addGuest(User user);

    void removeGuest(User user);
}
