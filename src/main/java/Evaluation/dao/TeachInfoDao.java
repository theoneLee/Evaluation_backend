package Evaluation.dao;

import Evaluation.entity.TeachInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Lee on 2017/11/7 0007.
 */
@Repository
public interface TeachInfoDao extends JpaRepository<TeachInfo,Integer> {


    TeachInfo findById(Integer integer);
}
