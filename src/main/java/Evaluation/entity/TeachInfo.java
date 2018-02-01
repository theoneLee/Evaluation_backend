package Evaluation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/10/17 0017.
 */
@Entity
public class TeachInfo {
    @Id
    @GeneratedValue
    private Integer id;
    private String position;
    private Date date;

    private String tid;//授课老师，此要接受一个tid

    @ManyToMany(mappedBy = "teachInfoList")
    private List<CommentTeacher> commentTeacherList;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<Comment> commentList=new ArrayList<Comment>();//评分列表，单向一对多

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public List<CommentTeacher> getCommentTeacherList() {
        return commentTeacherList;
    }

    public void setCommentTeacherList(List<CommentTeacher> commentTeacherList) {
        this.commentTeacherList = commentTeacherList;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @JsonBackReference
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
