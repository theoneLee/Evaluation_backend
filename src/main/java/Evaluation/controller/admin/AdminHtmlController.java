package Evaluation.controller.admin;

import Evaluation.entity.Admin;
import Evaluation.entity.Response;
import Evaluation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminHtmlController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/admin/test/{username}")
    public @ResponseBody Response addTestAdmin(@PathVariable String username) throws Exception {
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(username);
        adminService.save(admin);
        return new Response().success();
    }

    @GetMapping(value = "/admin/addAdmin")
    public String addAdminView(){
        return "/admin/addAdmin";
    }

    @PostMapping(value = "/admin/addAdmin/post")
    public String addAdmin(@Valid Admin admin, BindingResult errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("filedError","提交内容有空值");
            return "/admin/addAdmin";
        }
        try {
            adminService.save(admin);
        } catch (Exception e) {
            model.addAttribute("usernameError","username重复");
            e.printStackTrace();
            return "/admin/addAdmin";
        }
        return "redirect:/admin/allAdmin";
    }

    @GetMapping(value = "/admin/allAdmin")
    public String allAdminView(@RequestParam(value = "page",defaultValue = "0")int page,Model model){
        model.addAttribute("page",adminService.getAllAdminList(page));
        return "/admin/allAdmin";
    }

    @GetMapping(value = "/admin/updateAdmin")
    public String updateAdminView(int id, Model model){
        model.addAttribute("admin",adminService.getAdminById(id));
        return "/admin/updateAdmin";
    }

    @PostMapping(value = "/admin/updateAdmin/post")
    public String updateAdmin(@Valid Admin admin, BindingResult errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("filedError","提交内容有空值");
            return "/admin/updateAdmin?id="+admin.getId();
        }
        try {
            adminService.save(admin);
        } catch (Exception e) {
            model.addAttribute("usernameError","username重复");
            e.printStackTrace();
            return "/admin/updateAdmin?id="+admin.getId();
        }
        return "redirect:/admin/allAdmin";
    }

    @GetMapping(value = "/admin/deleteAdmin")
    public String deleteAdmin(int id){
        adminService.deleteAdmin(id);
        return "redirect:/admin/allAdmin";
    }
}
