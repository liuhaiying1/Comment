package com.comment.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "evaluate")
public class Evaluate implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long bussinessid;
    private Long userid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public Evaluate() {
    }

    public Evaluate(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBussinessid() {
        return bussinessid;
    }

    public void setBussinessid(Long bussinessid) {
        this.bussinessid = bussinessid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public float getOverall() {
        return overall;
    }

    public void setOverall(float overall) {
        this.overall = overall;
    }

    public float getFlavor() {
        return flavor;
    }

    public void setFlavor(float flavor) {
        this.flavor = flavor;
    }

    public float getEnv() {
        return env;
    }

    public void setEnv(float env) {
        this.env = env;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    private float overall;
    private float flavor;
    private float env;
    private float service;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(length = 500, nullable = false)
    private String message;
    @Column(length = 500, nullable = false)
    private String imgFile;
    @Column(length = 500, nullable = false)
    private String response;
    private int start;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @CreationTimestamp
    private Date createTime;

    public boolean getCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        collection = collection;
    }
    @Transient
    private  boolean collection;
}
