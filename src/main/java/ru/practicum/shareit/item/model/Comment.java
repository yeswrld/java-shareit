package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;
    @Column(name = "text")
    private String text;
    @JoinColumn(name = "author_id")
    @ManyToOne
    private User author;
    @JoinColumn(name = "item_id")
    @ManyToOne
    private Item item;
    @Column(name = "created")
    private LocalDateTime created;
}
