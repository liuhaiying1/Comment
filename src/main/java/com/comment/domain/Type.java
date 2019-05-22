package com.comment.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "type")
public class  Type  implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public Type() {
    }

    public Type(Long id) {
        this.id = id;
    }

    @Column(length = 50,nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
