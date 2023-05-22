package com.hellzzangAdmin.service;

import com.hellzzangAdmin.controller.BannerMgController;
import com.hellzzangAdmin.controller.GymWearMgController;
import com.hellzzangAdmin.dto.BannerDto;
import com.hellzzangAdmin.dto.GymWearDto;
import com.hellzzangAdmin.dto.QBannerDto;
import com.hellzzangAdmin.dto.QGymWearDto;
import com.hellzzangAdmin.entity.*;
import com.hellzzangAdmin.repository.AdminUserRepository;
import com.hellzzangAdmin.repository.FileRepository;
import com.hellzzangAdmin.repository.GymWearFileRepository;
import com.hellzzangAdmin.repository.GymWearRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

import static com.hellzzangAdmin.entity.QAdminUsers.adminUsers;
import static com.hellzzangAdmin.entity.QBanner.banner;
import static com.hellzzangAdmin.entity.QGymWear.gymWear;

/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : GymWearService
 * author         : 김재성
 * date           : 2023-05-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-22        김재성       최초 생성
 */

@Service
@RequiredArgsConstructor
public class GymWearService {

    private final AdminUserRepository adminUserRepository;
    private final GymWearRepository gymWearRepository;
    private final FileRepository fileRepository;
    private final GymWearFileRepository gymWearFileRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * @methodName : selectBannerList
     * @date : 2023-05-16 오후 1:52
     * @author : 김재성
     * @Description: 배너 페이징 조회
     **/
    public Page<GymWearDto> selectGymWearList(Pageable pageable){
        //admin 사용자 리스트 조회
        List<GymWearDto> content = getGymWearList(pageable);

        //admin 사용자 total 조회
        Long count = getCount();
        return new PageImpl<>(content, pageable, count);
    }

    /**
     * @methodName : getBannerList
     * @date : 2023-05-16 오후 1:51
     * @author : 김재성
     * @Description: 배너 리스트 조회
     **/
    private List<GymWearDto> getGymWearList(Pageable pageable) {

        List<GymWearDto> content = jpaQueryFactory
                .select(new QGymWearDto(
                        gymWear.id,
                        gymWear.title,
                        gymWear.contents,
                        gymWear.contentsText,
                        gymWear.adminUsers.username,
                        gymWear.createdDate,
                        gymWear.modifiedDate,
                        gymWear.thumbnailIdx,
                        gymWear.delYn
                ))
                .from(gymWear)
                .innerJoin(adminUsers).on(adminUsers.id.eq(gymWear.adminUsers.id))
                .where(gymWear.delYn.eq("N"))
                .orderBy(gymWear.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
//        return null;
    }

    /**
     * @methodName : getCount
     * @date : 2023-05-16 오후 1:51
     * @author : 김재성
     * @Description: 배너 total 조회
     **/
    private Long getCount(){
        Long count = jpaQueryFactory
                .select(gymWear.count())
                .from(gymWear)
                .innerJoin(adminUsers).on(adminUsers.id.eq(gymWear.adminUsers.id))
                .where(adminUsers.id.eq(gymWear.adminUsers.id))
                .where(gymWear.delYn.eq("N"))
                .fetchOne();
        return count;
    }

    @Transactional
    public void save(GymWearMgController.SaveGymWear saveGymWear, HttpServletRequest request){

        HttpSession session = request.getSession(true);
        AdminUsers adminUsers = adminUserRepository.findById((Long) session.getAttribute("id")).get();

        //GymWear 테이블 저장
        GymWear gymWear = GymWear.builder()
                .id(saveGymWear.getId())
                .title(saveGymWear.getTitle())
                .contents(saveGymWear.getContents())
                .contentsText(saveGymWear.getContents().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""))
                .adminUsers(adminUsers)
                .thumbnailIdx(saveGymWear.getThumbnailIdx())
                .build();

        Long gymWearId = gymWearRepository.save(gymWear).getId();

        //기존에 등록된 파일 전부 삭제
        if(gymWear.getId() != null){
            gymWearFileRepository.deleteByGymWearId(saveGymWear.getId());
        }

        //banner_file 테이블 저장
        List<Long> fileIdxList = saveGymWear.getContentFileIdx();

        int index = 0;

        for (Long id : fileIdxList) {
            FileInfo fileInfo = fileRepository.findById(fileIdxList.get(index)).get();

            GymWearFile gymWearFile = GymWearFile.builder()
                    .fileInfo(fileInfo)
                    .gymWearId(gymWearId)
                    .build();

            gymWearFileRepository.save(gymWearFile);
            index++;
        }
    }

}
