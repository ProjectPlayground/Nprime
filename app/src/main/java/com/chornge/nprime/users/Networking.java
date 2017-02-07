package com.chornge.nprime.users;

interface Networking {
    void addFollower(User user);

    void removeFollower(User user);

    void startFollowing(User user);

    void stopFollowing(User user);
}
