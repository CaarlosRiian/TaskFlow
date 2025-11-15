package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    private String title;
    private String description;
    private enum status {
        PENDENTE,
        EM_PROGRESSO,
        CONCLUIDA
    }
    private enum priority {
        ALTA,
        MEDIA,
        BAIXA
    }
    private LocalDateTime creation_date;
    private LocalDateTime due_date;

    @ManyToOne
    private User assigned_to;

    @ManyToOne
    private Project project_id;

    @OneToMany
    private List<Comment> comments;
}
