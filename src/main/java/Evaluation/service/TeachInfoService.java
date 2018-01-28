package Evaluation.service;

import Evaluation.dao.CommentTeacherDao;
import Evaluation.dao.TeachInfoDao;
import Evaluation.entity.Comment;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.TeachInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Service
public class TeachInfoService {
    @Autowired
    private TeachInfoDao teachInfoDao;
    @Autowired
    private CommentTeacherDao commentTeacherDao;

    private Logger logger= LoggerFactory.getLogger(getClass());

    public void saveComment(String infoId, Comment comment) {
        //找到该teachInfo,然后往里面加comment
        TeachInfo teachInfo=teachInfoDao.findById(Integer.valueOf(infoId));
        teachInfo.getCommentList().add(comment);
        teachInfoDao.save(teachInfo);//通过级联保存comment
    }

    public List<TeachInfo> getAllTeachInfo() {
        return teachInfoDao.findAll();
    }


    public TeachInfo getTeachInfoById(int id) {
        return teachInfoDao.findById(id);
    }


    @Transactional
    public void deleteTeachInfoById(int id) {
        teachInfoDao.deleteCommentTeacherTeachInfoRelationByTeachInfo(id);//todo 删除之前先删除中间表的内容，不然删不了，注意这个是多对多关系，所以不应该在删除commentTeacher的时候影响TeachInfo，反之亦然。一对多关系就可以利用级联来处理删除的业务
        teachInfoDao.delete(id);
    }

    public void save(TeachInfo teachInfo) {
        teachInfoDao.save(teachInfo);
    }


    public void update(TeachInfo teachInfo) {
        TeachInfo temp=teachInfoDao.findById(teachInfo.getId());//这样更新不会覆盖掉字段List<Comment> commentList，而不是直接teachInfoDao.save(teachInfo);
        temp.setTid(teachInfo.getTid());
        temp.setCommentList(teachInfo.getCommentList());
        temp.setPosition(teachInfo.getPosition());
        temp.setDate(teachInfo.getDate());
        teachInfoDao.save(temp);
    }


    public Page<TeachInfo> getAllTeachInfoList(int page) {
        int size=5;
        return teachInfoDao.findAll(new PageRequest(page,size));
    }

    public TeachInfo getTeachInfoWithCommentList(int id) {
        return teachInfoDao.findWithCommentListById(id);
    }

    /**
     * //通过teachInfoId找到teachInfo，通过id找到评课老师，将该teachInfo加入评课老师的teachInfo 队列
     * @param teachInfoId
     * @param commentTeacherId
     */
    @Transactional
    public void choiceCommentTeacher(int teachInfoId, int commentTeacherId) {
        //TeachInfo teachInfo=teachInfoDao.findWithCommentListById(teachInfoId);
//        TeachInfo teachInfo=teachInfoDao.findById(teachInfoId);
//        CommentTeacher commentTeacher=commentTeacherDao.findWithTeachInfoListById(commentTeacherId);//
//        System.out.println("teachInfo："+teachInfo.getId());
//        System.out.println("commentTeacher:"+commentTeacher.getId());
        System.out.println("teachInfoId:"+teachInfoId);
        System.out.println("commentTeacherId:"+commentTeacherId);
        commentTeacherDao.saveCommentTeacherTeachInfoRelation(commentTeacherId,teachInfoId);
    }

    public TeachInfo getTeachInfoWithCommentListByTid(String tid) {
        return teachInfoDao.findWithCommentListByTid(tid);
    }
}
