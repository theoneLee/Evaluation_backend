package Evaluation.controller.admin;

import Evaluation.entity.CommentTeacher;
import Evaluation.service.CommentTeacherService;
import Evaluation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class CommentTeacherHtmlController {
    @Autowired
    private CommentTeacherService commentTeacherService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/admin/addCommentTeacher")
    public String addCommentTeacherView(Model model,@RequestParam(value = "id") Long id){
        model.addAttribute("teacher",teacherService.getTeacherById(id));
        return "/admin/addCommentTeacher";
    }

    @PostMapping(value = "/admin/addCommentTeacher/post")
    public String addCommentTeacher(@Valid CommentTeacher commentTeacher, BindingResult errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("filedError",errors.getAllErrors());
            return "/admin/addCommentTeacher";
        }
        try {
            commentTeacherService.saveCommentTeacher(commentTeacher);
        } catch (Exception e) {
            if(e.getMessage().equals("存在相同tid")){
                model.addAttribute("tidError",e.getMessage());
                return "/admin/addCommentTeacher";
            }
            e.printStackTrace();
        }
        return "redirect:/admin/allCommentTeacher";
    }

    @GetMapping(value = "/admin/allCommentTeacher")
    public String allCommentTeacherView(@RequestParam(value = "page",defaultValue = "0")int page, Model model){
        model.addAttribute("page",commentTeacherService.getAllCommentTeacherList(page));
        return "/admin/allCommentTeacher";
    }

    @GetMapping(value = "/admin/updateCommentTeacher")
    public String updateCommentTeacherView(@RequestParam(value = "id")Long id,Model model){
        model.addAttribute("teacher",commentTeacherService.getCommentTeacherById(id));
        return "/admin/updateCommentTeacher";
    }

    @PostMapping(value = "/admin/updateCommentTeacher/post")
    public String updateCommentTeacher(@Valid CommentTeacher commentTeacher){
        commentTeacherService.updateCommentTeacher(commentTeacher);
        return "redirect:/admin/allCommentTeacher";
    }

    @GetMapping(value = "/admin/deleteCommentTeacher")
    public String deleteCommentTeacherView(@RequestParam(value = "id")Long id){
        commentTeacherService.deleteCommentTeacherById(id);
        return "redirect:/admin/allCommentTeacher";
    }


}
