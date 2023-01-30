package ru.kolyanpie.dlp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kolyanpie.dlp.room.Room;
import ru.kolyanpie.dlp.room.RoomService;
import ru.kolyanpie.dlp.user.UserService;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("/dlp/room")
public class RoomController {
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public RoomController(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public String create(@RequestHeader("DLP-USER-ID") String id, @RequestBody String roomName) throws ExecutionException {
        return roomService.createRoom(roomName, userService.getUser(id));
    }

    @PostMapping("/{roomId}/join")
    public void kick(@RequestHeader("DLP-USER-ID") String id, @PathVariable String roomId) throws ExecutionException {
        roomService.join(roomId, userService.getUser(id));
    }

    @PostMapping("/{roomId}/kick")
    public void kick(@RequestHeader("DLP-USER-ID") String id, @PathVariable String roomId, @RequestBody String targetId) throws ExecutionException {
        roomService.kick(roomId, userService.getUser(id), userService.getUser(targetId));
    }

    @PostMapping("/remove")
    public void remove(@RequestHeader("DLP-USER-ID") String id, @RequestBody String roomName) throws ExecutionException {
        roomService.removeRoom(roomName, userService.getUser(id));
    }

    @GetMapping("/list")
    public Map<String, Room> list() {
        return roomService.list();
    }
}
