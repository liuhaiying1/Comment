package com.comment.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name="user_recommend")
public class UserRecommend {

    public UserRecommend() {
    }

    public int getValue() {
        return value;
    }

    public UserRecommend(int value, Long userid, Long bussinessid) {
        this.value = value;
        this.userid = userid;
        this.bussinessid = bussinessid;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    private Long Id;

    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userId) {
        userid = userId;
    }

    public Long getBussinessId() {
        return bussinessid;
    }

    public void setBussinessId(Long bussinessId) {
        bussinessid = bussinessId;
    }

    private Long userid;
    private Long bussinessid;
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @CreationTimestamp
    private Date createTime;
}
