package Evaluation.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/10/17 0017.
 */
public class TeachInfo {
    private Integer id;
    private String position;
    private Date date;

    private Teacher teacher;//授课老师信息
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
