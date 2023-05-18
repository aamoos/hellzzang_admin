package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.repository
 * fileName       : UserRepository
 * author         : hj
 * date           : 2023-05-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-15        hj       최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLastLoginDate(LocalDateTime lastLogin);
}
