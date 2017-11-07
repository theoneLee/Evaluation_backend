package Evaluation.controller;

import Evaluation.entity.Response;
import Evaluation.entity.TeachInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Response saveTeachInfo(TeachInfo teachInfo){
        teachInfoService.save(teachInfo);
        return new Response().success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response updateTeachInfoById(TeachInfo teachInfo){
        teachInfoService.update(teachInfo);
        return new Response().success();
    }
}
