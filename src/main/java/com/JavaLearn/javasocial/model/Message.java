package com.JavaLearn.javasocial.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MESSAGES")
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @NotEmpty
    @JsonView(Views.IdName.class)
    private String text;

    @Column(name = "CREATION_DATE", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonView(Views.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message")
    @JsonView(Views.FullMessage.class)
    private List<Comment> comments;

    @JsonView(Views.FullMessage.class)
    private String link;

    @Column(name = "LINK_TITLE")
    @JsonView(Views.FullMessage.class)
    private String linkTitle;

    @Column(name = "LINK_DESCRIPTION")
    @JsonView(Views.FullMessage.class)
    private String linkDescription;

    @Column(name = "LINK_COVER")
    @JsonView(Views.FullMessage.class)
    private String linkCover;
}
