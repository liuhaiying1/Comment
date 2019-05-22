package com.comment.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user")
public class User  implements Serializable {
    public User() {
    }

    public User(Long id) {
        this.id=id;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 50,nullable = false)
    private String password;
    @Column(length = 50,nullable = false)
    private String chName;
    private long groupId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Column(length = 50,nullable = true)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(length = 50,nullable = true)
    private String email;
    @Column(length = 50,nullable = true)
    private String img;

}
