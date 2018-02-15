package com.todo.playground.hibernate;

import javax.persistence.*;

@Table(name = "address")
@Entity
public class AddressHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "city")
    public String city;

    public AddressHibernate() {
    }

    public AddressHibernate(String city) {
        this.city = city;
    }
}
