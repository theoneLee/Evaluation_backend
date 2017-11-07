package Evaluation.service;

import Evaluation.dao.TeacherDao;
import Evaluation.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lee on 2017/11/2 0002.
 */
@Service
public class TeacherService {

    @Autowired
    TeacherDao teacherDao;

    public List<String> getAllTeacherNameList() {
        List<String> list=teacherDao.getAllTeacherNameList();//返回所有老师姓名列表
        return list;
    }


    public void save(Teacher teacher) throws Exception {
        Teacher temp=teacherDao.findByTid(teacher.getTid());
        if (temp!=null){
            throw new Exception("存在相同tid");
        }
        teacherDao.save(teacher);
    }


    public Teacher getTeacherByTid(String tid) {//todo 要把密码内容清除。并且之后正式完成是将明文密码改为加密之后的
        return teacherDao.findByTid(tid);
    }


    public void deleteByTid(String tid) {
        Teacher teacher=teacherDao.findByTid(tid);
        teacherDao.delete(teacher);
    }

    public void update(Teacher teacher) {
        Teacher temp=teacherDao.findByTid(teacher.getTid());
        //System.out.println(temp);
        temp.setName(teacher.getName());
        temp.setPhone(teacher.getPhone());
        temp.setPassword(teacher.getPassword());
        temp.setApartment(teacher.getApartment());
        teacherDao.save(temp);
    }
}
