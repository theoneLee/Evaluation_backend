package Evaluation.controller;

import Evaluation.entity.Comment;
import Evaluation.entity.Response;
import Evaluation.entity.TeachInfo;
import Evaluation.service.TeachInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 1.管理员的TeachInfo的crud操作
 * Created by Lee on 2017/10/19 0019.
 */
@RestController
@RequestMapping(value = "/teachInfo")
public class TeachInfoController {

    @Autowired
    TeachInfoService teachInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public Response getAllTeachInfo(){
        List<TeachInfo> list=teachInfoService.getAllTeachInfo();
        return new Response().success(list);
    }

    @RequestMapping(value = "/id",method = RequestMethod.GET)
    public Response getTeachInfoById(String id){
        TeachInfo info=teachInfoService.getTeachInfoById(id);
        return new Response().success(info);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Response deleteTeachInfoById(String id){
        teachInfoService.deleteTeachInfoById(id);
        return new Response().success();
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Response saveTeachInfo(@RequestBody TeachInfo teachInfo){
        teachInfoService.save(teachInfo);
        return new Response().success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response updateTeachInfoById(@RequestBody TeachInfo teachInfo){
        teachInfoService.update(teachInfo);
        return new Response().success();
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Response initData() throws JsonProcessingException {
        TeachInfo teachInfo=new TeachInfo();
        teachInfo.setTeacherName("test1");
        teachInfo.setPosition("U111");
        teachInfo.setDate(new Date());

        List<Comment> list=teachInfo.getCommentList();


        for (int i=0;i<3;i++){
            Comment comment=new Comment();
            comment.setNum1(1);
            comment.setNum2(2);
            comment.setNum3(3);
            comment.setCommentTeacherId("1111");
            comment.setAdvice("asd");
            comment.setAudioUrl("www.1.com");
            comment.setVideoUrl("www.1.com");
            comment.setCourseName("courseName1");
            comment.setCourseInfo("CourseInfo1");

            list.add(comment);
        }

        teachInfoService.save(teachInfo);
        //ObjectMapper mapper = new ObjectMapper();
        //String res=mapper.writeValueAsString(teachInfo);
        return new Response().success();
    }
}
