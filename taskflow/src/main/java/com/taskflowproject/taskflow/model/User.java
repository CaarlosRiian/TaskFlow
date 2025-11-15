package com.taskflowproject.taskflow.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String email;
    private Role role;
    private boolean active = true;

    @OneToMany(mappedBy = "manager")
    private List<Project> projects;

    @OneToMany(mappedBy = "author")
    private List<Team> teams;

    @OneToMany(mappedBy = "author_comment")
    private List<Comment> comments;

    @OneToMany(mappedBy = "assigned_to")
    private List<Task> tasks;

}
