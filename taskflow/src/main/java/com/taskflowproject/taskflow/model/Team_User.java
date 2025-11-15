package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;

import java.util.*;

public class Team_User {

    @ManyToOne
    private List<User> user_id;

    @ManyToOne
    private List<Team> team_id;
}
