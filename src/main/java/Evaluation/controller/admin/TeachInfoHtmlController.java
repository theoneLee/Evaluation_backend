package Evaluation.controller.admin;

import Evaluation.entity.TeachInfo;
import Evaluation.entity.wrapper.TeachInfoWrapper;
import Evaluation.service.CommentTeacherService;
import Evaluation.service.TeachInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class TeachInfoHtmlController {

    @Autowired
    private TeachInfoService teachInfoService;

    @Autowired
    private CommentTeacherService commentTeacherService;

    @GetMapping(value = "/admin/addTeachInfo")
    public String addTeachInfoView(){
        return "/admin/addTeachInfo";
    }

    @PostMapping(value = "/admin/addTeachInfo/post")
    public String addTeachInfo(@Valid TeachInfoWrapper teachInfoWrapper, BindingResult errors, Model model) throws ParseException {//在不加@RequestBody的情况下，是否能够将表单元素包装为teacher？验证后是可以
        if (errors.hasErrors()){
            model.addAttribute("filedError",errors.getAllErrors());
            return "/admin/addTeachInfo";
        }
        String dateString=teachInfoWrapper.getDate().replace('T',' ');//使用包装类去处理接受的参数，再转化为teachInfo储存
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        TeachInfo teachInfo=new TeachInfo();
        teachInfo.setTid(teachInfoWrapper.getTid());
        teachInfo.setPosition(teachInfoWrapper.getPosition());
        teachInfo.setDate(sdf.parse(dateString));
        System.out.println(getClass()+":"+teachInfo.getDate());
        teachInfoService.save(teachInfo);
        return "redirect:/admin/allTeachInfo";
    }

    @GetMapping(value = "/admin/allTeachInfo")
    public String allTeachInfoView(@RequestParam(value = "page",defaultValue = "0")int page, Model model){
        model.addAttribute("page",teachInfoService.getAllTeachInfoList(page));
        return "/admin/allTeachInfo";
    }

    @GetMapping(value = "/admin/getTeachInfo")
    public String getTeachInfoByIdView(@RequestParam(value = "id")int id, Model model){
        TeachInfo info=teachInfoService.getTeachInfoWithCommentList(id);

        if (info==null||info.getCommentList()==null||info.getCommentList().size()==0){
            model.addAttribute("commentError","该课程评论为空");
        }else {
            model.addAttribute("commentList",info.getCommentList());
        }
        //model.addAttribute("infoId",id);
        return "/admin/getTeachInfo";
    }

    @GetMapping(value = "/admin/updateTeachInfo")
    public String updateTeachInfoView(@RequestParam(value = "id")int id, Model model){
        model.addAttribute("teachInfo",teachInfoService.getTeachInfoById(id));
        return "/admin/updateTeachInfo";
    }

    @PostMapping(value = "/admin/updateTeachInfo/post")
    public String updateTeachInfo(@Valid TeachInfoWrapper wrapper, BindingResult errors, Model model) throws ParseException {
        if (errors.hasErrors()){
            model.addAttribute("filedError",errors.getAllErrors());
            return "/admin/updateTeachInfo?id="+wrapper.getId();
        }

        String dateString=wrapper.getDate().replace('T',' ');//使用包装类去处理接受的参数，再转化为teachInfo储存
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TeachInfo teachInfo=new TeachInfo();
        teachInfo.setId(wrapper.getId());
        teachInfo.setTid(wrapper.getTid());
        teachInfo.setPosition(wrapper.getPosition());
        teachInfo.setDate(sdf.parse(dateString));

        teachInfoService.update(teachInfo);
        return "redirect:/admin/allTeachInfo";
    }

    @GetMapping(value = "/admin/deleteTeachInfo")
    public String deleteTeachInfo(@RequestParam(value = "id")int id){
        teachInfoService.deleteTeachInfoById(id);
        return "redirect:/admin/allTeachInfo";
    }


    /**
     *
     * @param teachInfoId 为TeachInfo 的id
     * @return
     */
    @GetMapping(value = "/admin/choiceCommentTeacher")
    public String choiceCommentTeacherView(@RequestParam(value = "page",defaultValue = "0")int page,@RequestParam(value = "teachInfoId")int teachInfoId,Model model){
        model.addAttribute("teachInfoId",teachInfoId);
        model.addAttribute("page",commentTeacherService.getAllCommentTeacherList(page));
        return "/admin/choiceCommentTeacher";
    }

    @GetMapping(value = "/admin/choiceCommentTeacher/post")
    public String choiceCommentTeacher(@RequestParam(value = "teachInfoId")int teachInfoId,@RequestParam(value = "id")int id){
        //通过teachInfoId找到teachInfo，通过id找到评课老师，将该teachInfo加入评课老师的teachInfo 队列
        teachInfoService.choiceCommentTeacher(teachInfoId,id);
        return "redirect:/admin/allTeachInfo";
    }


    @GetMapping(value = "/admin/seachTeachInfo")
    public String getTeachInfoByTid(@RequestParam(value = "tid",defaultValue = "-1")String tid, Model model){
        TeachInfo info=teachInfoService.getTeachInfoWithCommentListByTid(tid);

        if (info==null||info.getCommentList()==null||info.getCommentList().size()==0){
            model.addAttribute("commentError","该课程评论为空");
        }else {
            model.addAttribute("commentList",info.getCommentList());
        }
        //model.addAttribute("infoId",id);
        return "/admin/searchTeachInfo";
    }
}
