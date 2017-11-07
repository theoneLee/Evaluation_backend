package Evaluation.dao;

import Evaluation.entity.CommentTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Repository
public interface CommentTeacherDao extends JpaRepository<CommentTeacher,Long>{

    CommentTeacher findByTid(String tid);

}
