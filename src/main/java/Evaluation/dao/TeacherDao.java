package Evaluation.dao;

import Evaluation.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Repository
public interface TeacherDao extends JpaRepository<Teacher,Long>{

    Teacher findByTid(String tid);


    @Query("select t.name from Teacher t")
    List<String> getAllTeacherNameList();
}
