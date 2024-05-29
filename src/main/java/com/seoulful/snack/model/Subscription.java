package com.seoulful.snack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String mailing_address;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime created_on;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime subscribed_on;

    @Column(nullable = false)
    private LocalDateTime expired_on;

    @Column(nullable = false)
    private int quantity;

    public Subscription(Long id, User user, Product product, String mailing_address,
                        LocalDateTime subscribed_on, int quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.mailing_address = mailing_address;
        this.subscribed_on = subscribed_on;
        this.expired_on = subscribed_on.plusDays(30);
        this.quantity = quantity;
    }
}
