package com.hellzzangAdmin.repository;

import com.hellzzangAdmin.entity.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @methodName :
* @date : 2023-05-10 오후 1:29
* @author : 김재성
* @Description: 사용자 repository
**/

public interface UserRepository extends JpaRepository<AdminUsers, Long> {
    AdminUsers findByUserid(String userid);

}