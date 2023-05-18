package com.hellzzangAdmin.service;

import com.hellzzangAdmin.dto.QUserDto;
import com.hellzzangAdmin.dto.UserDto;
import com.hellzzangAdmin.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hellzzangAdmin.entity.QUser.user;

/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : UserService
 * author         : hj
 * date           : 2023-05-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-15        hj       최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
    * @methodName : selectUserInfo
    * @date : 2023-05-16 오전 10:52
    * @author : hj
    * @Description: 유저 리스트 출력 & 검색 시 출력
    **/
    public List<UserDto> selectUserInfo(String searchVal){

        List<UserDto> response = getUserInfo(searchVal);
        return response;
    }
    private List<UserDto> getUserInfo(String searchVal){

        List<UserDto> content = jpaQueryFactory
                .select(new QUserDto(
                        user.id,
                        user.nickname,
                        user.username,
                        user.joinDate,
                        user.dorYn,
                        user.delYn,
                        user.blockYn
                ))
                .from(user)
                .where(containsSearch(searchVal))
                .fetch();

        return content;
    }
    private BooleanExpression containsSearch(String searchVal){
        return searchVal != null ? user.username.contains(searchVal) : null;
    }

    /**
    * @methodName : saveUserBlockDay
    * @date : 2023-05-17 오전 10:09
    * @author : hj
    * @Description: 사용자 계정 정지 날짜 설정
    **/
    @Transactional
    public long saveUserBlockDay(Long id, String selectVal) {
        int selectBlockDay = Integer.parseInt(selectVal);
        LocalDateTime currentDate = LocalDateTime.now(); //오늘 날짜

//        LocalDateTime blockDate = currentDate.plusDays(selectBlockDay); //정지 풀리는 날짜


        LocalDateTime blockDate = currentDate.plusMinutes(selectBlockDay); //정지 풀리는 날짜 tes를 위해 초단위를 더했음



        String blockDateFormat = blockDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //저장될 패턴
        blockDate = LocalDateTime.parse(blockDateFormat, dateFormatter); //String 데이터를 LocalDateTime 형태로 파싱

        if(selectBlockDay >= 0) {
            return jpaQueryFactory.update(user)
                    .set(user.blockDate, blockDate)
                    .set(user.blockYn, "Y")
                    .where(user.id.eq(id))
                    .execute();
        }
        else{
            return jpaQueryFactory.update(user)
                    .set(user.blockDate, blockDate)
                    .set(user.blockYn, "N")
                    .where(user.id.eq(id))
                    .execute();
        }
    }
}
