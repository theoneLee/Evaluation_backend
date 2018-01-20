package Evaluation.controller;

import Evaluation.entity.Response;
import Evaluation.entity.Teacher;
import Evaluation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 1.获取所有教师姓名列表（以便给管理员配置TeachInfo的老师信息时使用）
 * 2.教师的crud
 * Created by Lee on 2017/10/19 0019.
 */
@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(method = RequestMethod.GET)
    public Response getAllTeacherList(){
        List<String> list=teacherService.getAllTeacherNameList();
        return new Response().success(list);
    }

    /**
     * todo 要检查接受到的Teacher值，不能让全部都为null
     * @param teacher
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Response saveTeacher(@RequestBody Teacher teacher) throws Exception {
        teacherService.save(teacher);
        return new Response().success();
    }

    /**
     * 注意输入/ssss这类的url时仍然会产生查询，要利用Exception提示报错信息
     * @param tid
     * @return
     */
    @RequestMapping(value = "/{tid:\\d+}",method = RequestMethod.GET)
    public Response getTeacherByTid(@PathVariable String tid){
        Teacher teacher=teacherService.getTeacherByTid(tid);
        return new Response().success(teacher);
    }

    @RequestMapping(value = "/{tid:\\d+}",method = RequestMethod.DELETE)
    public Response deleteTeacherByTid(@PathVariable String tid){
        teacherService.deleteByTid(tid);
        return new Response().success();
    }

    /**
     * 新增teacher时手动输tid。
     * 更新teacher时将tid放入隐藏域，并在前端封装为Teacher
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/{tid:\\d+}",method = RequestMethod.PUT)
    public Response updateTeacherByTid(@RequestBody Teacher teacher){
        teacherService.update(teacher);
        return new Response().success();
    }

}
