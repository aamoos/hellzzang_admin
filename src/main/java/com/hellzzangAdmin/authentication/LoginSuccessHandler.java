package com.hellzzangAdmin.authentication;

import com.google.gson.JsonObject;
import com.hellzzangAdmin.common.CoTopComponent;
import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * packageName    : com.hellzzangAdmin.config
 * fileName       : LoginSuccessHandler
 * author         : 김재성
 * date           : 2023-05-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */
/* 로그인 성공시 타는 handler */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends CoTopComponent implements AuthenticationSuccessHandler {

    private final AdminUserRepository adminUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {

        //default 성공
        String resultCode = "00";

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 60 * 3);

        AdminUsers adminUsers = adminUserRepository.findByUserid(auth.getName());
        session.setAttribute("id", adminUsers.getId());

        //마지막 로그인시간 insert
//        userService.updateLastLoginDate(((UserInfo) auth.getPrincipal()));

        //Response 결과 값을 넣어줌
        JsonObject loginResult = new JsonObject();
        loginResult.addProperty("resultCode", resultCode);
        loginResult.addProperty("targetUrl", request.getContextPath()+"/");

        //응답 전송
        writeResponse(response, loginResult);
    }

}
