package Evaluation.controller;

import Evaluation.entity.Comment;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.Response;
import Evaluation.entity.TeachInfo;
import Evaluation.entity.wrapper.CommentWrapper;
import Evaluation.service.CommentTeacherService;
import Evaluation.service.TeachInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 1.评课教师在客户端的登陆注销
 * 2.评课教师获取他的待评课列表
 * 3.评课教师的crud
 * 4.评课教师的评课操作
 * 5.评课教师上传音频视频
 * Created by Lee on 2017/10/19 0019.
 */
@RestController
@RequestMapping(value = "/commentTeacher")
public class CommentTeacherController {

    @Autowired
    private CommentTeacherService commentTeacherService;

    @Autowired
    private TeachInfoService teachInfoService;

    @RequestMapping(value = "/teachInfo",method = RequestMethod.GET)
    public Response getTeachInfoByCommentTeacherTid(String tid){
        //评课教师获取他的待评课列表
        List<TeachInfo> list=commentTeacherService.getTeachInfoByCommentTeacherTid(tid);
        return new Response().success(list);
    }

    /**
     * 在客户端上的待评课列表上获取到该课程id
     * 根据TeachInfo的id，对其上传内容（评课）进行存储
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Response saveCommentForTeachInfo(String infoId,Comment comment){
        teachInfoService.saveComment(infoId,comment);
        return new Response().success();
    }


    /**
     * 管理员可以在创建CommentTeacher时就把TeachInfoList置入，也可以直接置入一个空元素之后在update中调整
     * @param teacher
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response saveCommentTeacher(CommentTeacher teacher){
        commentTeacherService.saveCommentTeacher(teacher);//todo 注意CommentTeacher关联上的TeachInfoList
        return new Response().success();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Response deleteCommentTeacher(String tid){
        commentTeacherService.deleteCommentTeacher(tid);//删除该对象要顺便把CommentTeacher的TeachInfoList也删除，使用级联即可
        return new Response().success();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getCommentTeacherByTid(String tid){
        CommentTeacher teacher=commentTeacherService.getCommentTeacherByTid(tid);
        return new Response().success(teacher);
    }

    /**
     * 在前端包装好所有数据（接受一个json）
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response updateCommentTeacher(CommentTeacher teacher){
        commentTeacherService.updateCommentTeacherByTid(teacher);
        return new Response().success();
    }


    //todo 上传视频。音频


}
