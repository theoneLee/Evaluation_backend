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
        //teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));//todo 为什么数据库存明文，取出来在加密和前端给的比较就是可以通过matches方法，但是再数据库先存了加密后的密码，取出来与前端的匹配就是不通过？经过测试发现，在同一个class下同时进行encode和matcher是能够通过的，而跨越就不行
        //logger.info(passwordEncoder.encode(s));
        //logger.info(String.valueOf(passwordEncoder.matches(s,teacher.getPassword())));
        logger.info(teacher.getPassword());
        return teacher;
    }
}
