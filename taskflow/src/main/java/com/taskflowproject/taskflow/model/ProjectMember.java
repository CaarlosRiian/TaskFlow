package com.taskflowproject.taskflow.model;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_member_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User projectUser;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectJoined;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleProject;

    @OneToMany(mappedBy = "projectMember")
    private List<TeamMember> teamMembers;
}
