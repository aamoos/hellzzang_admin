package com.hellzzangAdmin.authentication;

/**
 * packageName    : com.hellzzangAdmin.config
 * fileName       : CustomAuthenticationProvider
 * author         : 김재성
 * date           : 2023-05-10
 * description    : 로그인 인증 provider
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;

/**
 * 전달받은 사용자의 아이디와 비밀번호를 기반으로 비즈니스 로직을 처리하여 사용자의 ‘인증’에 대해서 검증을 수행하는 클래스입니다.
 * CustomAuthenticationFilter로 부터 생성한 토큰을 통하여 ‘UserDetailsService’를 통해 데이터베이스 내에서 정보를 조회합니다.
 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailService userDetailService;

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        //아이디 값이 비었을경우
        if("".equals(userId)) {
            throw new BadCredentialsException("idNullException");
        }else if("".equals(userPw)) {
            //패스워드 값이 비어있을경우
            throw new BadCredentialsException("pwdNullException");
        }

        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetails userInfo = userDetailService.loadUserByUsername(userId);

        //사용자가 없을경우
        if (userInfo == null) {
            throw new BadCredentialsException("idException");
        }else if(userInfo != null ) {
            //패스워드체크
            if(!(passwordEncoder.matches(userPw, userInfo.getPassword()))) {
                throw new BadCredentialsException("pwdException");
            }
        }
        return new UsernamePasswordAuthenticationToken(userInfo, userPw, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
