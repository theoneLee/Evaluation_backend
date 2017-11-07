package Evaluation.service;

import Evaluation.dao.TeachInfoDao;
import Evaluation.entity.Comment;
import Evaluation.entity.TeachInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Service
public class TeachInfoService {
    @Autowired
    private TeachInfoDao teachInfoDao;


    public void saveComment(String infoId, Comment comment) {
        //找到该teachInfo,然后往里面加comment
        TeachInfo teachInfo=teachInfoDao.findById(Integer.valueOf(infoId));
        teachInfo.getCommentList().add(comment);
        teachInfoDao.save(teachInfo);//通过级联保存comment
    }
}
