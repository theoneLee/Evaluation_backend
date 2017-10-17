package Evaluation.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/10/17 0017.
 */
public class CommentTeacher extends Teacher{
    private Integer id;
    private List<TeachInfo> list=new ArrayList<TeachInfo>();

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
