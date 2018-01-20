package Evaluation.auth;

import Evaluation.dao.CommentTeacherDao;
import Evaluation.entity.CommentTeacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 校验用户的认证和授权
 */
@Component
public class MyUserDetailService implements UserDetailsService{
    @Autowired
    private CommentTeacherDao dao;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private Logger logger= LoggerFactory.getLogger(getClass());

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {//只要一个实体实现了UserDetails接口，然后在UserDetailsService实现类上重写这个loadUserByUsername方法即可完成自定义用户的认证和授权

        CommentTeacher teacher=dao.findByTid(s);
        return teacher;
    }
}
