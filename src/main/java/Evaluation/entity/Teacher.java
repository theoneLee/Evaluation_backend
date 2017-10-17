package Evaluation.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/10/17 0017.
 */
public class Teacher {
    private Integer id;
    protected String tid;//工号
    protected String phone;
    protected String apartment;

    protected String password;
//    private boolean isComment;
//    private boolean isTeach;
//
//    private List<TeachInfo> list=new ArrayList<TeachInfo>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
