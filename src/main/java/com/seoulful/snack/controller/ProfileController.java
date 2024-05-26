package com.seoulful.snack.controller;

import com.seoulful.snack.dto.RequestResponse;
import com.seoulful.snack.model.User;
import com.seoulful.snack.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/api")
@CrossOrigin("*")   // Allow requests to load resources on other servers. https://spring.io/guides/gs/rest-service-cors
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/user")
    public ResponseEntity getUser(@Valid @RequestParam String email) {
        User user = profileService.getProfile(email);
        if (user != null) {
            user.setPassword(null); // Remove the current password before returning, for security
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //update profile for an account
    @PostMapping("/update")
    public ResponseEntity<RequestResponse> updateProfile(@Valid @RequestBody RequestResponse updateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.updateProfile(updateRequest));
    }
}