package com.taskflowproject.taskflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    private String name;
    private String description;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CONCLUIDA,
        EM_REVISAO,
        DESENVOLVIMENTO,
        CANCELADA
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference(value = "project-task")
    private List<Task> tasks;

    @OneToMany(mappedBy = "projectJoined")
    private List<ProjectMember> members;

}
