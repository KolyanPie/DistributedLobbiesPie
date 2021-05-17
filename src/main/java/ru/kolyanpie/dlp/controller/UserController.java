package ru.kolyanpie.dlp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kolyanpie.dlp.user.User;
import ru.kolyanpie.dlp.user.UserService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/dlp/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody User user) {
        log.info("user = {}", user);
        return userService.loginUser(user.getId(), user.getName());
    }

    @PostMapping("/logout")
    public void userLogout(@RequestHeader("DLP-USER-ID") String id, @RequestBody String name) {
        userService.logoutUser(id, name);
    }

    @GetMapping("list")
    public Map<String, String> list() {
        return userService.list();
    }
}
