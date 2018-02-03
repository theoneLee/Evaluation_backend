package Evaluation.controller;

import Evaluation.auth.MD5Util;
import Evaluation.entity.Comment;
import Evaluation.entity.CommentTeacher;
import Evaluation.entity.Response;
import Evaluation.entity.TeachInfo;
import Evaluation.entity.wrapper.CommentWrapper;
import Evaluation.service.CommentTeacherService;
import Evaluation.service.TeachInfoService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private HttpSession session;
    @Autowired
    private CommentTeacherService commentTeacherService;

    @Autowired
    private TeachInfoService teachInfoService;
    private Logger logger= LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/teachInfo",method = RequestMethod.GET)
    public Response getTeachInfoByCommentTeacherTid( String tid,String token) throws Exception {
        //评课教师获取他的待评课列表
        checkToken(tid,token);
        List<TeachInfo> list=commentTeacherService.getTeachInfoByCommentTeacherTid(tid);
        return new Response().success(list);
    }

    private void checkToken(  String tid, String token) throws Exception {

//        logger.info("checkToken_Tid",tid);
//        logger.info("checkToken_Token",token);
        System.out.println("checkToken_Token:"+token);
        String res= (String) session.getAttribute(tid);
        System.out.println("real_Token:"+res);
        if (res==null){
            throw new Exception("session不含该tid，请重新登陆");
        }
        if (!res.equals(token)){
            throw new Exception("请重新登陆");
        }
    }

    /**
     * 在客户端上的待评课列表上获取到该课程id
     * 根据TeachInfo的id，对其上传内容（评课）进行存储
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/comment/{infoId:\\d+}",method = RequestMethod.POST)//todo 正则匹配数字
    public Response saveCommentForTeachInfo(@PathVariable String infoId,@RequestBody Comment comment){
        //checkToken(request,tid,token);todo
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

    @RequestMapping(value = "/{tid:\\w+}",method = RequestMethod.DELETE)//todo 正则表达式匹配\w 即匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。
    public Response deleteCommentTeacher(@PathVariable String tid){
        commentTeacherService.deleteCommentTeacher(tid);//删除该对象要顺便把CommentTeacher的TeachInfoList也删除，使用级联即可
        return new Response().success();
    }

    @RequestMapping(value = "/{tid:\\w+}",method = RequestMethod.GET)
    public Response getCommentTeacherByTid(@PathVariable String tid){
        logger.info(tid);
        CommentTeacher teacher=commentTeacherService.getCommentTeacherByTid(tid);
        return new Response().success(teacher);
    }

    /**
     * 在前端包装好所有数据（接受一个json）
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/{tid:\\w+}",method = RequestMethod.PUT)
    public Response updateCommentTeacher(@RequestBody CommentTeacher teacher){
        commentTeacherService.updateCommentTeacherByTid(teacher);
        return new Response().success();
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Response initData(String tid) throws Exception {
        CommentTeacher teacher=new CommentTeacher();
        teacher.setName("带commentList");
        teacher.setPassword(tid);
        teacher.setApartment("aa");
        teacher.setPhone("111");
        teacher.setTid(tid);

        List<TeachInfo> list=teacher.getTeachInfoList();
        for (int i=0;i<1;i++){
            TeachInfo info=new TeachInfo();
            info.setPosition("U111");
            info.setDate(new Date());
            info.setTid(tid);
            //这里先不加teachInfo里面的CommentList，之后要添加或更新数据时要整个数据都拿取，然后不更新的保留id，新增的不添加id (以证明可行)
            for (int j=0;j<3;j++){
                Comment comment=new Comment();
                comment.setCommentTeacherId("lzw0001");
                comment.setCourseName("course"+j);
                comment.setNum1(j);
                comment.setNum2(j);
                comment.setNum3(j);
                info.getCommentList().add(comment);
            }

            list.add(info);
        }
        commentTeacherService.saveCommentTeacher(teacher);
        return new Response().success();
    }

    //上传视频。音频
    /**
     * 上传视频
     * @param file
     * @return 该资源路径，这个路径会被comment一起保存
     */
    @RequestMapping(value = "/upload/video", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("file") MultipartFile file) {
        //checkToken(request,tid,token);todo
        try {
            FileUtils.writeByteArrayToFile(new File("target/classes/static/video/" + file.getOriginalFilename()), file.getBytes());// 保存路径写法，要保存在target上，注意还要有static这种spring boot帮你配置的静态资源路径（才能扫描）
        } catch (IOException e) {
            e.printStackTrace();
           // return new Response().failure("文件上传失败");
        }
        return new Response().success("http://localhost:8080/video/" + file.getOriginalFilename());
    }

    /**
     * 上传音频
     * @param file
     * @return 该资源路径，这个路径会被comment一起保存
     */
    @RequestMapping(value = "/upload/audio", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadAudio(@RequestParam("file") MultipartFile file) {
        //checkToken(request,tid,token);todo
        try {
            FileUtils.writeByteArrayToFile(new File("target/classes/static/audio/" + file.getOriginalFilename()), file.getBytes());// 保存路径写法，要保存在target上，注意还要有static这种spring boot帮你配置的静态资源路径（才能扫描）
        } catch (IOException e) {
            e.printStackTrace();
            //return new Response().failure("文件上传失败");
        }
        return new Response().success("http://localhost:8080/audio/" + file.getOriginalFilename());
    }

    //todo 需不需要单独修改CommentTeacher里面的TeachInfo？目前解决方案是直接使用update方法做一次整体覆盖

    //登录注销
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(  String tid, String password) throws Exception {
        CommentTeacher commentTeacher=commentTeacherService.login(tid,password);//这里返回一个包含tid和token，之后app访问api需要认证该token
        LoginWrapper wrapper=new LoginWrapper();
        wrapper.setTid(commentTeacher.getTid());
        wrapper.setToken(UUID.randomUUID().toString().replace('-','_'));
        session.setAttribute(tid,wrapper.getToken());
//        logger.info("session:",session.getAttributeNames().hasMoreElements());
        return new Response().success(wrapper);
    }
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Response logout( String tid) {
        session.removeAttribute(tid);
        logger.info("session:",session.getAttribute(tid));
        System.out.println(session.getAttributeNames().hasMoreElements());
        return new Response().success("退出成功");
    }

    class LoginWrapper{
        private String tid;
        private String token;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
