package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long team_id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User leader;

    @OneToMany(mappedBy = "teamMember")
    private List<TeamMember> teamMembers;


}


