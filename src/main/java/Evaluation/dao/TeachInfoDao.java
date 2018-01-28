package Evaluation.dao;

import Evaluation.entity.TeachInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Repository
public interface TeachInfoDao extends JpaRepository<TeachInfo,Integer> {


    TeachInfo findById(Integer integer);

    @Query("select n from TeachInfo n left join fetch n.commentList where n.id=?1")
    TeachInfo findWithCommentListById(Integer id);

    @Query("select n from TeachInfo n left join fetch n.commentList where n.tid=?1")
    TeachInfo findWithCommentListByTid(String tid);

    @Modifying
    @Query(value = "DELETE FROM comment_teacher_teach_info WHERE comment_teacher_id=?1 AND teach_info_id=?2", nativeQuery = true)
    void deleteCommentTeacherTeachInfoRelation(int commentTeacherId, int teachInfoId);

    @Modifying
    @Query(value = "DELETE FROM comment_teacher_teach_info WHERE comment_teacher_id=?1", nativeQuery = true)
    void deleteCommentTeacherTeachInfoRelationByCommentTeacher(Long commentTeacherId);

    @Modifying
    @Query(value = "DELETE FROM comment_teacher_teach_info WHERE teach_info_id=?1", nativeQuery = true)
    void deleteCommentTeacherTeachInfoRelationByTeachInfo(int teachInfoId);
}
