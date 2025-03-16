package ru.practicum.shareit.request;

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
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "requester")
    @ManyToOne
    private User requester;
    @Column(name = "created")
    private LocalDateTime created;
}
