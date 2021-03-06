package Evaluation.service;

import Evaluation.auth.MD5Util;
import Evaluation.dao.CommentTeacherDao;
import Evaluation.dao.TeachInfoDao;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.TeachInfo;
import Evaluation.entity.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lee on 2017/11/2 0002.
 */
@Service
public class CommentTeacherService {
    @Autowired
    private CommentTeacherDao commentTeacherDao;

    @Autowired
    private TeachInfoDao teachInfoDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger= LoggerFactory.getLogger(getClass());

    public void saveCommentTeacher(CommentTeacher teacher) throws Exception {
        //todo 在controller中转化json为对象时可以直接把关联关系也建立么？
//        if (teacher.getList()!=null){
//            //建立关联
//        }
        if (commentTeacherDao.findByTid(teacher.getTid())!=null){
            throw new Exception("存在相同tid");
        }
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));//对密码加密后再保存到数据库，等到验证时spring security会自动调用passwordEncoder的matcher方法进行匹配
        //teacher.setPassword(MD5Util.encode(teacher.getPassword()));//在存入数据库时对密码进行md5加密,等到验证时spring security会自动调用的刚新增passwordEncoder里复写的matcher方法进行匹配
        commentTeacherDao.save(teacher);
    }

    public void deleteCommentTeacher(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        commentTeacherDao.delete(Long.valueOf(teacher.getId()));
    }


    public CommentTeacher getCommentTeacherByTid(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        teacher.setPassword(null);
        logger.info("CommentTeacher",teacher);
        return teacher;
    }

    public void updateCommentTeacherByTid(CommentTeacher teacher) {
        String tid=teacher.getTid();
        CommentTeacher temp=commentTeacherDao.findByTid(tid);
        temp.setName(teacher.getName());
        temp.setPhone(teacher.getPhone());
        temp.setApartment(teacher.getApartment());
        temp.setPassword(teacher.getPassword());
        temp.setTeachInfoList(teacher.getTeachInfoList());//这里从http上接收到的teacher能够直接将关联关系也一并反序列化为对象
        commentTeacherDao.save(temp);
    }

    public List<TeachInfo> getTeachInfoByCommentTeacherTid(String tid) {
        CommentTeacher teacher=commentTeacherDao.findByTid(tid);
        return teacher.getTeachInfoList();
    }

    public CommentTeacher login(String tid,String password) throws Exception {
        CommentTeacher temp=commentTeacherDao.findByTid(tid);

        if (null==temp){
            throw new Exception("tid不存在");
        }
        if (!passwordEncoder.matches(password,temp.getPassword())){//自己手动验证密码，不能使用equals方法来判断，因为加密方式时sha256+salt，使用equals一定是失败的
            throw new Exception("密码错误");
        }
        temp.setPassword(null);
        return temp;
    }

    public Page<CommentTeacher> getAllCommentTeacherList(int page) {
        int size=5;
        return commentTeacherDao.findAll(new PageRequest(page,size));
    }

    public CommentTeacher getCommentTeacherById(Long id) {
        return commentTeacherDao.findOne(id);
    }

    public void updateCommentTeacher(CommentTeacher teacher) {
        //这里修改时不能够直接save(teacher)，这样会把List<TeachInfo> list字段给覆盖掉
        String tid=teacher.getTid();
        CommentTeacher temp=commentTeacherDao.findByTid(tid);
        temp.setName(teacher.getName());
        temp.setPhone(teacher.getPhone());
        temp.setApartment(teacher.getApartment());
        //temp.setList(teacher.getList());
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        commentTeacherDao.save(temp);//这里保存从数据库找到的teacher，就保留有List<TeachInfo>
    }

    @Transactional
    public void deleteCommentTeacherById(Long id) {
        teachInfoDao.deleteCommentTeacherTeachInfoRelationByCommentTeacher(id);//删除时要先删除中间表的关系
        commentTeacherDao.delete(id);
    }
}
