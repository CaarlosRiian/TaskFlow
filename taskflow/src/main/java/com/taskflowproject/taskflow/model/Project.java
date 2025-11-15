package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;
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
    private enum status {
        CONCLUIDA,
        EM_REVISAO,
        DESENVOLVIMENTO,
        CANCELADA
    }

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

}
