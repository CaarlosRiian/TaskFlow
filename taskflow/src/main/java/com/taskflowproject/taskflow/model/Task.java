package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime creation_date;
    private LocalDateTime due_date;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

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



}
