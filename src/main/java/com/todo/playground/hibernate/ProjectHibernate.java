package com.todo.playground.hibernate;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Table(name = "project")
@Entity
public class ProjectHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @ManyToMany(fetch = EAGER, mappedBy = "projects")
    public List<UserHibernate> users;

    public ProjectHibernate() {
    }

    public ProjectHibernate(String name) {
        this.name = name;
    }
}
