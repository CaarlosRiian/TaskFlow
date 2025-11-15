package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    private String message;
    private LocalDateTime date_time;

    @ManyToOne
    private User author_comment;

    @ManyToOne
    private Task task_id;
}
