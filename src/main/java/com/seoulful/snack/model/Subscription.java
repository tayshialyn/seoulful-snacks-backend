package com.seoulful.snack.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Product product;

    @ManyToOne
    User user;

    int quantity;
    String mailingAddress;
    Date subscribedOn;
    Date expiredOn;
}
