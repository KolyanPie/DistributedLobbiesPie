package ru.kolyanpie.dlp.room;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.kolyanpie.dlp.user.User;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RoomService {

    private final Map<String, Room> publicRooms = new HashMap<>();

    public String createRoom(String roomName, User owner) {
        Room room = new Room(roomName, owner);
        String id = RandomStringUtils.randomAlphanumeric(8);
        publicRooms.put(id, room);
        return id;
    }

    public void join(String roomId, User user) {
        if (!publicRooms.containsKey(roomId)) {
            throw new IllegalArgumentException();
        }
        publicRooms.get(roomId).join(user);
    }

    public void kick(String roomId, User owner, User target) {
        if (!publicRooms.containsKey(roomId)) {
            throw new IllegalArgumentException();
        }
        if (!publicRooms.get(roomId).isOwner(owner)) {
            throw new IllegalArgumentException();
        }
        publicRooms.get(roomId).kick(target);
    }

    public void removeRoom(String roomId, User owner) {
        if (!publicRooms.containsKey(roomId)) {
            throw new IllegalArgumentException();
        }
        if (!publicRooms.get(roomId).isOwner(owner)) {
            throw new IllegalArgumentException();
        }
        publicRooms.remove(roomId);
    }

    public Map<String, Room> list() {
        return publicRooms;
    }
}
