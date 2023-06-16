package com.hellzzangAdmin.authentication;

import com.google.gson.JsonObject;
import com.hellzzangAdmin.common.CoTopComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : com.hellzzangAdmin.config
 * fileName       : LoginFailureHandler
 * author         : 김재성
 * date           : 2023-05-10
 * description    : 로그인 실패 handler
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */
/* 로그인 실패시 타는 핸들러 */

@Slf4j
@Component
public class LoginFailureHandler extends CoTopComponent implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        writeResponse(response, parseException(request.getParameter("un"), exception));
    }

    private JsonObject parseException(String userName, AuthenticationException exception) {
        String errCode = "99";
        String errMsg = exception.getMessage();

        JsonObject result = new JsonObject();
        result.addProperty("resultCode", errCode);
        result.addProperty("resultMessage", errMsg);
        return result;
    }
}
