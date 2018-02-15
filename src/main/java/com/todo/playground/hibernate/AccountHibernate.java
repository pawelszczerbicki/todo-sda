package com.todo.playground.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class AccountHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "company")
    public String company;

    @OneToOne(mappedBy="account")
    public UserHibernate user;

    public AccountHibernate() {
    }

    public AccountHibernate(String company) {
        this.company = company;
    }
}
