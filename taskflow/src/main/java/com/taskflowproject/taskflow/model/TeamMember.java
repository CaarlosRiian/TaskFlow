package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long team_member_id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "project_member_id")
    private ProjectMember projectMember;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role teamRole;

}
