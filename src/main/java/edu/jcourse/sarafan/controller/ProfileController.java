package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.dto.UserSubscriptionDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.UserService;
import edu.jcourse.sarafan.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final UserSubscriptionService userSubscriptionService;

    @GetMapping("/{id}")
    @JsonView(View.FullProfile.class)
    public UserDto get(@PathVariable String id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/subscribe")
    public UserSubscriptionDto subscribe(@RequestBody UserSubscriptionDto userSubscription) {
        return userSubscriptionService.subscribe(userSubscription);
    }
}