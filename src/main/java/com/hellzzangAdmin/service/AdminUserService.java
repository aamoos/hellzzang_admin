package com.hellzzangAdmin.service;

import com.hellzzangAdmin.controller.AdminMgController;
import com.hellzzangAdmin.dto.AdminUsersDto;
import com.hellzzangAdmin.dto.QAdminUsersDto;
import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.repository.AdminUserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import static com.hellzzangAdmin.entity.QAdminUsers.adminUsers;

/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : AdminUserService
 * author         : 김재성
 * date           : 2023-05-12
 * description    : admin 사용자 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-12        김재성       최초 생성
 */
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final PasswordEncoder passwordEncoder;
    /**
    * @methodName : find
    * @date : 2023-05-12 오후 4:04
    * @author : 김재성
    * @Description: 사용자 정보 조회
    **/
    public AdminUsers find(Long id){
        return adminUserRepository.findById(id).get();
    }

    /**
     * @methodName : save
     * @date : 2023-05-12 오후 1:20
     * @author : 김재성
     * @Description: admin 사용자 저장
     **/
    @Transactional
    public Long save(@Valid AdminMgController.SaveAdminUser saveAdminUser){
        saveAdminUser.setPassword(passwordEncoder.encode(saveAdminUser.getPassword()));
        AdminUsers adminUsers = saveAdminUser.toEntity();

        return adminUserRepository.save(adminUsers).getId();
    }

    /**
    * @methodName : delete
    * @date : 2023-05-15 오전 10:52
    * @author : 김재성
    * @Description: 관리자 삭제
    **/
    @Transactional
    public Long delete(Long id){
        AdminUsers adminUsers = adminUserRepository.findById(id).get();
        adminUsers.delete();
        return adminUsers.getId();
    }

    /**
    * @methodName : selectAdminUserList
    * @date : 2023-05-12 오후 1:53
    * @author : 김재성
    * @Description: 사용자 리스트 조회
    **/
    public Page<AdminUsersDto> selectAdminUserList(String searchVal, Pageable pageable){
        //admin 사용자 리스트 조회
        List<AdminUsersDto> content = getAdminUserList(searchVal, pageable);

        //admin 사용자 total 조회
        Long count = getCount(searchVal);
        return new PageImpl<>(content, pageable, count);
    }

    /**
    * @methodName : getAdminUserList
    * @date : 2023-05-12 오후 1:41
    * @author : 김재성
    * @Description: admin 사용자 리스트 조회
    **/
    private List<AdminUsersDto> getAdminUserList(String searchVal, Pageable pageable) {

        List<AdminUsersDto> content = jpaQueryFactory
                .select(new QAdminUsersDto(
                        adminUsers.id,
                        adminUsers.userid,
                        adminUsers.username,
                        adminUsers.delYn,
                        adminUsers.createdDate
                ))
                .from(adminUsers)
                .where(containsSearch(searchVal))
                .where(adminUsers.delYn.eq("N"))
                .orderBy(adminUsers.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    /**
    * @methodName : getCount
    * @date : 2023-05-12 오후 1:41
    * @author : 김재성
    * @Description: admin 사용자 count 조회
    **/
    private Long getCount(String searchVal){
        Long count = jpaQueryFactory
                .select(adminUsers.count())
                .from(adminUsers)
                .where(containsSearch(searchVal))
                .where(adminUsers.delYn.eq("N"))
                .fetchOne();
        return count;
    }

    private BooleanExpression containsSearch(String searchVal){
        return searchVal != null && !searchVal.equals("") ? adminUsers.username.contains(searchVal) : null;
    }
}
