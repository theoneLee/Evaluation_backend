package Evaluation.controller.admin;

import Evaluation.entity.Teacher;
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
public class TeacherHtmlController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/admin/addTeacher")
    public String addTeacherView(){
        return "/admin/addTeacher";
    }

    @PostMapping(value = "/admin/addTeacher/post")
    public String addTeacher(@Valid Teacher teacher, BindingResult errors, Model model) {//在不加@RequestBody的情况下，是否能够将表单元素包装为teacher？
        if (errors.hasErrors()){
            model.addAttribute("filedError",errors.getAllErrors());
            return "/admin/addTeacher";
        }
        try {
            teacherService.save(teacher);

        } catch (Exception e) {
            if(e.getMessage().equals("存在相同tid")){
                model.addAttribute("tidError",e.getMessage());
                return "/admin/addTeacher";
            }
            e.printStackTrace();
        }
        return "redirect:/admin/allTeacher";
    }


    @GetMapping(value = "/admin/allTeacher")
    public String allTeacherView(@RequestParam(value = "page",defaultValue = "0")int page, Model model){
        model.addAttribute("page",teacherService.getAllTeacherList(page));
        return "/admin/allTeacher";
    }



}
