package ru.kolyanpie.dlp.room;

import lombok.Getter;
import ru.kolyanpie.dlp.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Room {
    private final String name;
    private final User owner;
    private final List<User> users = new ArrayList<>();

    public Room(String name, User owner) {
        this.name = name;
        this.owner = owner;
        users.add(owner);
    }

    public boolean isOwner(User user) {
        return owner.equals(user);
    }

    public void join(User user) {
        if (users.contains(user)) {
            throw new IllegalArgumentException();
        }
        users.add(user);
    }

    public void kick(User user) {
        if (!users.contains(user)) {
            throw new IllegalArgumentException();
        }
        users.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name) && owner.equals(room.owner) && users.equals(room.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner);
    }
}
