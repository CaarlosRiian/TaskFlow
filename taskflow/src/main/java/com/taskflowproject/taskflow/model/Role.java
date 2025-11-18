package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "roleProject")
    private List<ProjectMember> rolesProject;

    @OneToMany(mappedBy = "teamRole")
    private List<TeamMember> teamMembers;

}

