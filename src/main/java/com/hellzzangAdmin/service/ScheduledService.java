package com.hellzzangAdmin.service;

import com.hellzzangAdmin.controller.UserMgController;
import com.hellzzangAdmin.entity.User;
import com.hellzzangAdmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : ScheduledService
 * author         : hj
 * date           : 2023-05-17
 * description    : 스케쥴러 관련 service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-17        hj       최초 생성
 */
@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final UserRepository userRepository;
    private UserMgController userMgController;
    
    LocalDateTime currentDate = LocalDateTime.now(); //현재 날짜시간
    DateTimeFormatter dateFormatterYMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //변경할 날짜패턴(년월일시분초)

    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "gidwns617@naver.com";
    private final SpringTemplateEngine thymeleafTemplateEngine;

    /**
    * @methodName : checkUserBlockDate
    * @date : 2023-05-17 오후 2:00
    * @author : hj
    * @Description: 정지된 계정 날짜 체크 스케쥴러
    **/
    @Scheduled(cron = "0/59 * * * * *") //50초마다 실행(분 단위로 체크)
    public void checkUserBlockDate(){
        String currentDateFormat = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        currentDate = LocalDateTime.parse(currentDateFormat, dateFormatterYMDHMS);

        List<User> userList = userRepository.findAll();
        for(User user : userList){
            //해당 유저의 정지 날짜
            LocalDateTime userBlockDate = user.getBlockDate();
            String blockDateFormat = userBlockDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            userBlockDate = LocalDateTime.parse(blockDateFormat, dateFormatterYMDHMS);

            if("Y".equals(user.getBlockYn())) {
                if (currentDate.isAfter(userBlockDate)) {
                    System.out.println("---------정지확인-------" + currentDate + "-------------------");
                    user.setBlockYn("N");
                    System.out.println(user.getNickname() + "님의 계정정지가 해제되었습니다.");
                    userRepository.save(user);
                }
            }
        }
    }

    /**
     * @methodName : setDormancy
     * @date : 2023-05-17 오후 4:51
     * @author : hj
     * @Description: 마지막 로그인 이후 ~ 지났을 때 휴면 계정 처리
     **/
    @Scheduled(cron = "0 0/1 * * * *")
    public void setDormancy() throws MessagingException, IOException {
        currentDate = LocalDateTime.now().minusMinutes(2); //마지막 접속일자 체크하는 기준
        String currentDateFormat = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        currentDate = LocalDateTime.parse(currentDateFormat, dateFormatterYMDHMS);

        List<User> userList = userRepository.findByLastLoginDate(currentDate);
        for(User user : userList){
            if(!"admin".equals(user.getUserid())) {
                System.out.println("---------휴면처리------" + currentDate + "----------------------");
                System.out.println(user.getUserid());
                user.setDorYn("Y");
                userRepository.save(user);
                sendEmail(user.getUserid(), currentDate);
                System.out.println("--------------------휴면처리 완료--------------------");
            }
        }
    }
    @Transactional
    public void sendEmail(String userid, LocalDateTime currentDate) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String currentDateFormat = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // create the Thymeleaf context object and add the name variable
        Context thymeleafContext = new Context();

        int useridIdx = userid.indexOf("@");
        String emailFront = userid.substring(0, useridIdx);
        String emailBack = userid.substring(useridIdx + 1);
        String[] emailFrontWords = emailFront.split("");
        for(int i = 4; i<emailFrontWords.length ; i++){
            emailFrontWords[i] = "*";
        }
        String joinEmailFront = String.join("", emailFrontWords);
        String fullUserId = joinEmailFront+"@"+emailBack;

        thymeleafContext.setVariable("userid", fullUserId);
        thymeleafContext.setVariable("currentDate", currentDateFormat);

        // generate the HTML content from the Thymeleaf template
        String htmlContent = thymeleafTemplateEngine.process("/views/email/email.html", thymeleafContext);


        helper.setTo(userid);
        helper.setFrom(FROM_ADDRESS);
        helper.setSubject("<Hellzzang> 회원님의 계정이 휴면 상태로 처리되었습니다.");
        helper.setText(htmlContent, true);
        mailSender.send(message);
        System.out.println("메일 전송 완료 ----------------------------------------");
    }
}
