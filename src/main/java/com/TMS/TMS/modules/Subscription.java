package com.TMS.TMS.modules;

import com.TMS.TMS.status.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "subscription")
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

    @ManyToOne
    private Plan plan;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean autoRenew = false;

    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.ACTIVE;

}
