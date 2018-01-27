package Evaluation.dao;

import Evaluation.entity.CommentTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Repository
public interface CommentTeacherDao extends JpaRepository<CommentTeacher,Long>{

    CommentTeacher findByTid(String tid);


    @Query("select n from CommentTeacher n left join fetch n.teachInfoList where n.id=?1")
    CommentTeacher findWithTeachInfoListById(long id);//左外join fetch可以允许n.list为空，如果使用inner join fetch时，右边为空，会导致整体返回为空

    /**
     * 对CommentTeacher和TeachInfo的中间表 直接插入记录，用来增加关系。
     * todo 因为已有关系的情况下，通过teachInfoId找到teachInfo，通过id找到评课老师，将该teachInfo加入评课老师的teachInfo 队列
     * todo 这种方式会报错Duplicate entry '1' for key 'UK_rmjxvrx0siwmg21nujmhtahif'
     * @param commentTeacherId
     * @param teachInfoId
     */
    @Modifying
    @Query(value = "INSERT INTO comment_teacher_teach_info VALUES(?1, ?2)", nativeQuery = true)
    void saveCommentTeacherTeachInfoRelation(int commentTeacherId, int teachInfoId);
}
