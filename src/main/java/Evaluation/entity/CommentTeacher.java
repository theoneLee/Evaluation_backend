package Evaluation.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Lee on 2017/10/17 0017.
 */
@Entity
public class CommentTeacher{
    @Id
    @GeneratedValue
    private Integer id;
    private String tid;//工号
    private String name;
    private String phone;
    private String apartment;

    private String password;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<TeachInfo> list=new ArrayList<TeachInfo>();

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TeachInfo> getList() {
        return list;
    }

    public void setList(List<TeachInfo> list) {
        this.list = list;
    }

}
