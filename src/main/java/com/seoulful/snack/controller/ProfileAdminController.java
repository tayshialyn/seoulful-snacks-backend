package com.seoulful.snack.controller;

import com.seoulful.snack.model.User;
import com.seoulful.snack.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/profile")
@CrossOrigin("*")   // Allow requests to load resources on other servers. https://spring.io/guides/gs/rest-service-cors
public class ProfileAdminController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/list-all-users")
    public ResponseEntity getAllUsers() {
        List<User> users = profileService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}