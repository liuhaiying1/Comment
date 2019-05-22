package com.comment.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "business")
public class Business  implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long userid;
    private int status;
    private  int pre;  //浏览量
    private float sum;

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public Business(Long id) {
        this.id=id;
    }

    public Business() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 50,nullable = false)
    private String title;
    @Column(length = 50,nullable = false)
    private String bussinessFile;
    @Column(length = 500,nullable = false)
    private String imgFileName;
    @Column(length = 500,nullable = false)
    private String envFile;
    @Column(length = 50,nullable = false)
    private String phone;
    @Column(length = 100,nullable = false)
    private String address;
    @Column(length = 100,nullable = false)
    private String bussinessHours;
    @Column(length = 50,nullable = false)
    private String percapiita;
    private float flavor;
    private float env;
    private float service;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(length = 100)
    private String city;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBussinessFile() {
        return bussinessFile;
    }

    public void setBussinessFile(String bussinessFile) {
        this.bussinessFile = bussinessFile;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getEnvFile() {
        return envFile;
    }

    public void setEnvFile(String envFile) {
        this.envFile = envFile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBussinessHours() {
        return bussinessHours;
    }

    public void setBussinessHours(String bussinessHours) {
        this.bussinessHours = bussinessHours;
    }

    public String getPercapiita() {
        return percapiita;
    }

    public void setPercapiita(String percapiita) {
        this.percapiita = percapiita;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    private float lng;
    private float lat;
}
