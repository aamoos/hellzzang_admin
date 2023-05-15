package com.hellzzangAdmin.authentication;

import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.hellzzangAdmin.config
 * fileName       : UserDetailService
 * author         : 김재성
 * date           : 2023-05-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final AdminUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AdminUsers adminUser = userRepository.findByUserid(userId);

        if(adminUser == null){
            throw  new UsernameNotFoundException("해당 사용자가 존재하지 않습니다.");
        }

        return new org.springframework.security.core.userdetails.User(
                adminUser.getUsername(),
                adminUser.getPassword(),
                adminUser.getAuthorities()
        );
    }


}
