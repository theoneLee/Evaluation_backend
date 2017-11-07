package Evaluation;

import Evaluation.dao.TeachInfoDao;
import Evaluation.entity.Comment;
import Evaluation.entity.TeachInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;


/**
 * Created by Lee on 2017/5/23 0023.
 */
@SpringBootApplication
public class App {

//    @Autowired
//    private TeachInfoDao teachInfoDao;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);

        //new App().initData();
    }

//    private void initData() {
//        //System.out.println(teachInfoDao);
//        TeachInfo teachInfo=new TeachInfo();
//        teachInfo.setTeacherName("test1");
//        teachInfo.setPosition("U111");
//        teachInfo.setDate(new Date());
//
//        List<Comment> list=teachInfo.getCommentList();
//
//
//        for (int i=0;i<3;i++){
//            Comment comment=new Comment();
//            comment.setNum1(1);
//            comment.setNum2(2);
//            comment.setNum3(3);
//            comment.setCommentTeacherId("1111");
//            comment.setAdvice("asd");
//            comment.setAudioUrl("www.1.com");
//            comment.setVideoUrl("www.1.com");
//            comment.setCourseName("courseName1");
//            comment.setCourseInfo("CourseInfo1");
//
//            list.add(comment);
//        }
//
//        teachInfoDao.save(teachInfo);
//    }
}
