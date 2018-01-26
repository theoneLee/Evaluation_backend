package Evaluation.service;

import Evaluation.dao.AdminDao;
import Evaluation.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(Admin admin) throws Exception {
        Admin temp=dao.findByUsername(admin.getUsername());
        if (temp!=null){
            throw new Exception("具有相同username");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        dao.save(admin);

    }
}
