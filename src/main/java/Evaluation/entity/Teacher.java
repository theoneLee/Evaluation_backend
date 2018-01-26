package Evaluation.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/10/17 0017.
 */
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String tid;//工号
    private String name;
    private String phone;
    private String apartment;

//    @NotBlank
//    private String password;

////    private boolean isComment;
////    private boolean isTeach;
//    private List<TeachInfo> list=new ArrayList<TeachInfo>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
