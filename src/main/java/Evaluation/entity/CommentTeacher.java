package Evaluation.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * todo 这里的继承能不能直接就这样能确立关联关系？
 * Created by Lee on 2017/10/17 0017.
 */
@Entity
public class CommentTeacher extends Teacher{
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
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
