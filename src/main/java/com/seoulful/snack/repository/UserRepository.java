package com.seoulful.snack.repository;

import com.seoulful.snack.model.EnumRole;
import com.seoulful.snack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    ArrayList<User> findAllByRole(EnumRole role);
}

