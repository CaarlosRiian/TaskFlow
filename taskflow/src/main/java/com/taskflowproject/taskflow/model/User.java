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
    private String password;
    private boolean active = true;

    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> tasks;

    @OneToMany(mappedBy = "author")
    private List<Comment> commentsUser;

    @OneToMany(mappedBy = "leader")
    private List<Team> teamsLeader;

    @OneToMany(mappedBy = "projectUser")
    private List<ProjectMember> projectMemberships;

}
