package Evaluation.controller.admin;

import Evaluation.entity.Admin;
import Evaluation.entity.Response;
import Evaluation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminHtmlController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/test/{username}")
    public @ResponseBody Response addTestAdmin(@PathVariable String username) throws Exception {
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(username);
        adminService.save(admin);
        return new Response().success();
    }
}
