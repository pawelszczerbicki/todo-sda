package com.todo.playground;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserHibernate {

    @Id
    public Long id;

    @Column(name = "NAME")
    public String name;

    @Override
    public String toString() {
        return "UserHibernate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


