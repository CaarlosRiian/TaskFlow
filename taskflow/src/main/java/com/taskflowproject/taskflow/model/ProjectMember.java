package com.taskflowproject.taskflow.model;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long projectMemberId;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "projectMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers;
}
