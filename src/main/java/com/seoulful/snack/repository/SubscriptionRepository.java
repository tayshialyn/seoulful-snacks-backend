package com.seoulful.snack.repository;

import com.seoulful.snack.model.Subscription;
import com.seoulful.snack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // derived query get customer subscription
    public List<Subscription> findByUser(User user);
 }
