package Evaluation.dao;

import Evaluation.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);
}
