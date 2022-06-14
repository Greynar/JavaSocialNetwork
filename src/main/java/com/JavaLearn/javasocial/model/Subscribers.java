package com.JavaLearn.javasocial.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_SUBSCRIPTIONS")
@Data
public class Subscribers {
    @Id
    @Column(name = "SUBSCRIBER_ID")
    private String subscriberID;
    @Column(name = "CHANNEL_ID")
    private String channelId;
}
