package com.comment.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="collection")
public class Collection  implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long userid;
    private long bussinessid;
    @Column(length = 50)
    private String bussinessName;

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(length = 500)
    private String message;

    public Long getEvaluateid() {
        return evaluateid;
    }

    public void setEvaluateid(Long evaluateid) {
        this.evaluateid = evaluateid;
    }

    private Long evaluateid;
    private int type;

    public Collection() {
    }
    public Collection(long id) {
        this.id=id;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getBussinessid() {
        return bussinessid;
    }

    public void setBussinessid(long bussinessid) {
        this.bussinessid = bussinessid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
