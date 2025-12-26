package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;


import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonBackReference(value = "user-task")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference(value = "project-task")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    public enum Status {
        PENDENTE,
        EM_PROGRESSO,
        CONCLUIDA
    }

    public enum Priority {
        ALTA,
        MEDIA,
        BAIXA
    }



}
