package com.comment.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "food")
public class Food  implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long typeid;
    private Long sort;

    public int getIfrecommend() {
        return ifrecommend;
    }

    public void setIfrecommend(int ifrecommend) {
        this.ifrecommend = ifrecommend;
    }

    private int ifrecommend;

    public Food() {
    }

    public Food(Long id) {
        this.id=id;
    }

    private Long bussinessid;

    @Column(length = 50,nullable = false)
    private String food;

    @Column(length = 50,nullable = false)
    private String introduce;
    private float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public Long getBussinessid() {
        return bussinessid;
    }

    public void setBussinessid(Long bussinessid) {
        this.bussinessid = bussinessid;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
