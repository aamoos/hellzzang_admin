package com.hellzzangAdmin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * packageName    : com.hellzzangAdmin.config
 * fileName       : SecurityConfig
 * author         : 김재성
 * date           : 2023-05-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final UserDetailService userDetailService;
    private final DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(bCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()            //csrf 토큰을 비활성화
                .authorizeRequests()        //요청 URL에 따라 접근 권한을 설정
                .antMatchers("/auth/**").permitAll()
                .anyRequest()                //다른 모든 요청
                .authenticated()            //인증된 유저만 접근을 허용
                .and()
                .formLogin()                //로그인 폼
                .loginPage("/auth/login")    //로그인 페이지 설정
                .loginProcessingUrl("/login_proc")    //해당 URL로 요청이 오면 스프링 시큐리티가 가로채서 로그인처리 -> loadUserByName
                .successHandler(loginSuccessHandler)    //로그인 성공시 요청할 핸들러
                .failureHandler(loginFailureHandler)    //로그인 실패시 요청할 핸들러
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))    //로그아웃 url
                .logoutSuccessUrl("/auth/login")        //로그아웃 성공시 url
                .invalidateHttpSession(true)            //인증정보를 지우고 세션을 무효화
                .deleteCookies("JSESSIONID", "remember-me")        //jsessionid 쿠키 삭제
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)                    //세션 최대 허용수 1, -1인 경우 무제한 세션 허용
                .maxSessionsPreventsLogin(false)    //true면 중복 로그인 막음, false면 이전 로그인 세션 해제
                .expiredUrl("/auth/login")        //세션이 만료된경우 이동할 페이지
                .and()
                .and().rememberMe()                //로그인유지
//    		.alwaysRemember(false)		//// 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
                .userDetailsService(userDetailService)
                .tokenRepository(tokenRepository())  //DataSource 추가
                // 항상 기억할 것인지 여부
//    		.tokenValiditySeconds(43200)	// 쿠키의 만료시간 설정(초), default: 14일
                .rememberMeParameter("remember-me");
        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/css/**", "/favicon");
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        // JDBC 기반의 tokenRepository 구현체
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // dataSource 주입
        return jdbcTokenRepository;
    }
}
