package ru.kolyanpie.dlp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolyanpie.dlp.user.UserService;

@RestController
@Slf4j
@RequestMapping("/dlp/guest")
@ConditionalOnProperty(value = "api.guest.enabled", havingValue = "true")
public class GuestController {
    private final UserService userService;

    @Autowired
    public GuestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String guestLogin(@RequestBody String name) {
        return userService.loginGuest(name);
    }
}
