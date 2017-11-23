package Evaluation.controller;

import Evaluation.entity.Comment;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.Response;
import Evaluation.entity.TeachInfo;
import Evaluation.entity.wrapper.CommentWrapper;
import Evaluation.service.CommentTeacherService;
import Evaluation.service.TeachInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    public Response saveCommentForTeachInfo(String infoId,@RequestBody Comment comment){
        teachInfoService.saveComment(infoId,comment);
        return new Response().success();
    }


    /**
     * 管理员可以在创建CommentTeacher时就把TeachInfoList置入，也可以直接置入一个空元素之后在update中调整
     * @param teacher todo 注意该teacher要做非null校验
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response saveCommentTeacher(@RequestBody CommentTeacher teacher) throws Exception {
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
    public Response updateCommentTeacher(@RequestBody CommentTeacher teacher){
        commentTeacherService.updateCommentTeacherByTid(teacher);
        return new Response().success();
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Response initData() throws Exception {
        CommentTeacher teacher=new CommentTeacher();
        teacher.setName("test1");
        teacher.setPassword("111");
        teacher.setApartment("aa");
        teacher.setPhone("111");
        teacher.setTid("1111");

        List<TeachInfo> list=teacher.getList();
        for (int i=0;i<3;i++){
            TeachInfo info=new TeachInfo();
            info.setPosition("U111");
            info.setDate(new Date());
            info.setTeacherName("test1");
            //todo 这里先不加teachInfo里面的CommentList，之后要添加或更新数据时要整个数据都拿取，然后不更新的保留id，新增的不添加id (以证明可行)

            list.add(info);
        }
        commentTeacherService.saveCommentTeacher(teacher);
        return new Response().success();
    }

    //todo 上传视频。音频

    //todo 需不需要单独修改CommentTeacher里面的TeachInfo？目前解决方案是直接使用update方法做一次整体覆盖

    //todo 登录注销
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(String tid,String password) throws Exception {
        CommentTeacher commentTeacher=commentTeacherService.login(tid,password);//这里返回一个包含tid
        return new Response().success(commentTeacher);
    }
}
