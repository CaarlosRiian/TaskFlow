package com.taskflowproject.taskflow.model;

import jakarta.persistence.*;
import java.util.*;

public class Project_User {

    @OneToMany
    private List<Project> project_id;

    @OneToMany
    private List<User> user_id;
}
