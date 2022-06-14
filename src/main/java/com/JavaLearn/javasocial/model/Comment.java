package com.JavaLearn.javasocial.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS")
@Data
@EqualsAndHashCode(of = { "id" })
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdName.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User author;
}