package com.seoulful.snack.service;

import com.seoulful.snack.dto.RequestResponse;
import com.seoulful.snack.model.User;
import com.seoulful.snack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getProfile(String email) {
        User user;
        try {
            user = userRepository.findByEmail(email).orElseThrow();
            return user;
        } catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    // signs up a user and stores the user to the User table
    public RequestResponse updateProfile(RequestResponse updateRequest) {

        RequestResponse requestResponse = new RequestResponse();

        var user = userRepository.findByEmail(updateRequest.getEmail()).orElseThrow();

        user.setName(updateRequest.getName());
        user.setEmail(updateRequest.getEmail());
        user.setBillingAddress(updateRequest.getBillingAddress());

        // updateProfile does not update password
//        if (updateRequest.getPassword().isBlank())
//            throw new PasswordBlankException();
//        user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

        User userResult = userRepository.save(user);

        if (userResult != null && userResult.getId() > 0) {
            requestResponse.setUser(userResult);
            requestResponse.setMessage("User saved successfully.");
        }

        return requestResponse;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAll();
//            users = userRepository.findAllByRole(EnumRole.USER);
            return users;
        } catch (Exception e) {
            return null;
        }
    }
}