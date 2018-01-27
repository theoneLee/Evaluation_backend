package Evaluation.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by Lee on 2017/10/17 0017.
 */
@Entity
public class CommentTeacher implements UserDetails{
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String tid;//工号
    private String name;
    private String phone;
    private String apartment;

    @NotBlank
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(name = "comment_teacher_teach_info", joinColumns = {
            @JoinColumn(name = "CommentTeacher_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "TeachInfo_ID", referencedColumnName = "ID")})
    private List<TeachInfo> teachInfoList=new ArrayList<TeachInfo>();

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("comment");
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return tid;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TeachInfo> getTeachInfoList() {
        return teachInfoList;
    }

    public void setTeachInfoList(List<TeachInfo> teachInfoList) {
        this.teachInfoList = teachInfoList;
    }
}
