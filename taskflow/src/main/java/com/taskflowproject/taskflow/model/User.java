package com.taskflowproject.taskflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
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

    @JsonIgnore
    private String password;

    private boolean active = true;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;

    @OneToMany(mappedBy = "assignedTo")
    @JsonManagedReference(value = "user-task")
    private List<Task> tasks;

    @OneToMany(mappedBy = "author")
    private List<Comment> commentsUser;

    @OneToMany(mappedBy = "leader")
    private List<Team> teamsLeader;

    @OneToMany(mappedBy = "projectUser")
    private List<ProjectMember> projectMemberships;

}
