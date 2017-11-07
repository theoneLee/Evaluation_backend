package Evaluation.service;

import Evaluation.dao.CommentTeacherDao;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.TeachInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lee on 2017/11/2 0002.
 */
@Service
public class CommentTeacherService {
    @Autowired
    private CommentTeacherDao commentTeacherDao;


    public void saveCommentTeacher(CommentTeacher teacher) {
        //todo 在controller中转化json为对象时可以直接把关联关系也建立么？
//        if (teacher.getList()!=null){
//            //建立关联
//        }
        commentTeacherDao.save(teacher);
    }

    public void deleteCommentTeacher(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        commentTeacherDao.delete(Long.valueOf(teacher.getId()));
    }


    public CommentTeacher getCommentTeacherByTid(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        return teacher;
    }

    public void updateCommentTeacherByTid(CommentTeacher teacher) {
        String tid=teacher.getTid();
        CommentTeacher temp=commentTeacherDao.findByTid(tid);
        temp.setName(teacher.getName());
        temp.setPhone(teacher.getPhone());
        temp.setApartment(teacher.getApartment());
        temp.setPassword(teacher.getPassword());
        temp.setList(teacher.getList());//todo 这里假设从http上接收到的teacher能够直接将关联关系也一并反序列化为对象
        commentTeacherDao.save(temp);
    }

    public List<TeachInfo> getTeachInfoByCommentTeacherTid(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        return teacher.getList();
    }
}
