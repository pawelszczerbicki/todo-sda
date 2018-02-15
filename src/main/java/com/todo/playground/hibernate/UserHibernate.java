package com.todo.playground.hibernate;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "USERS")
public class UserHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "login")
    public String login;

    @Column(name = "password", nullable = false, length = 100)
    public String password;

    @Column(name = "created_at")
    @CreationTimestamp
    public LocalDateTime createdAt;

    //TODO show error when no cascade object references an unsaved transient instance
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "account_id")
    public AccountHibernate account;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name ="address_id")
    public AddressHibernate address;

    @ManyToMany(cascade = { ALL }, fetch = LAZY)
    @JoinTable(
            name = "user_project",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") })
    public List<ProjectHibernate> projects;

    @Override
    public String toString() {
        return "UserHibernate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", account=" + account +
                ", address=" + address +
                ", projects=" + projects +
                '}';
    }
}


