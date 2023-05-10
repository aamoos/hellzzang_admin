package com.hellzzangAdmin.common;

import com.google.gson.JsonObject;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : com.hellzzangAdmin.common
 * fileName       : CoTopComponent
 * author         : 김재성
 * date           : 2023-05-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-10        김재성       최초 생성
 */
/* 최상위 컴포넌트 */

public abstract class CoTopComponent {
    protected void writeResponse(HttpServletResponse res, JsonObject jsonObject) throws IOException {
        writeResponse(res, jsonObject.toString());
    }

    protected void writeResponse(HttpServletResponse res, String message) throws IOException{
        res.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        PrintWriter pw = res.getWriter();
        pw.write(message);
        pw.close();
    }

    protected String makePageDispatcherUrl(String requestUrl, String dispatcherPath) {
        return "/tiles/ajax" + dispatcherPath + requestUrl.substring(requestUrl.lastIndexOf("/"));
    }
}
