package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "project_member_id", nullable = false)
    private ProjectMember projectMember;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}