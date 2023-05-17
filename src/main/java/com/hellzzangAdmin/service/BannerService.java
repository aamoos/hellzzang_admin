package com.hellzzangAdmin.service;

import com.hellzzangAdmin.controller.BannerMgController;
import com.hellzzangAdmin.dto.BannerDto;
import com.hellzzangAdmin.dto.BannerFileDto;
import com.hellzzangAdmin.dto.QBannerDto;
import com.hellzzangAdmin.dto.QBannerFileDto;
import com.hellzzangAdmin.entity.AdminUsers;
import com.hellzzangAdmin.entity.Banner;
import com.hellzzangAdmin.entity.BannerFile;
import com.hellzzangAdmin.entity.FileInfo;
import com.hellzzangAdmin.repository.AdminUserRepository;
import com.hellzzangAdmin.repository.BannerFileRepository;
import com.hellzzangAdmin.repository.BannerRepository;
import com.hellzzangAdmin.repository.FileRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
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
import static com.hellzzangAdmin.entity.QBannerFile.bannerFile;
import static com.querydsl.core.types.ExpressionUtils.count;

/**
 * packageName    : com.hellzzangAdmin.service
 * fileName       : BannerService
 * author         : 김재성
 * date           : 2023-05-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-16        김재성       최초 생성
 */

@Service
@RequiredArgsConstructor
public class BannerService {

    private final AdminUserRepository adminUserRepository;
    private final BannerRepository bannerRepository;
    private final BannerFileRepository bannerFileRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final FileRepository fileRepository;

    /**
    * @methodName : find
    * @date : 2023-05-17 오후 1:42
    * @author : 김재성
    * @Description: banner
    **/
    public Banner find(Long id){
        return bannerRepository.findById(id).get();
    }

    /**
    * @methodName : findBannerFileList
    * @date : 2023-05-17 오후 3:52
    * @author : 김재성
    * @Description: 배너 파일 첨부 리스트 조회
    **/
    public List<BannerFileDto> findBannerFileList(Long id){
        List<BannerFileDto> content = jpaQueryFactory
                .select(new QBannerFileDto(
                         bannerFile.fileInfo.id
                        ,bannerFile.fileInfo.delYn
                        ,bannerFile.fileInfo.extension
                        ,bannerFile.fileInfo.originFileName
                        ,bannerFile.fileInfo.regDate
                        ,bannerFile.fileInfo.savedFileName
                        ,bannerFile.fileInfo.size
                        ,bannerFile.fileInfo.uploadDir
                ))
                .from(bannerFile)
                .where(bannerFile.fileInfo.delYn.eq("N"))
                .where(bannerFile.bannerId.eq(id))
                .orderBy(bannerFile.id.desc())
                .fetch();

        return content;
    }

    /**
    * @methodName : selectBannerList
    * @date : 2023-05-16 오후 1:52
    * @author : 김재성
    * @Description: 배너 페이징 조회
    **/
    public Page<BannerDto> selectBannerList(Pageable pageable){
        //admin 사용자 리스트 조회
        List<BannerDto> content = getBannerList(pageable);

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
    private List<BannerDto> getBannerList(Pageable pageable) {

        List<BannerDto> content = jpaQueryFactory
                .select(new QBannerDto(
                        banner.id,
                        banner.bannerPath,
                        banner.delYn,
                        banner.adminUsers.id,
                        adminUsers.username,
                        ExpressionUtils.as(
                                JPAExpressions.select(count(bannerFile.id))
                                        .from(bannerFile, banner)
                                        .where(bannerFile.bannerId.eq(banner.id)),
                                "fileTotal")
                ))
                .from(banner)
                .innerJoin(adminUsers).on(adminUsers.id.eq(banner.adminUsers.id))
                .where(banner.delYn.eq("N"))
                .orderBy(banner.id.desc())
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
                .select(banner.count())
                .from(banner)
                .innerJoin(adminUsers).on(adminUsers.id.eq(banner.adminUsers.id))
                .where(adminUsers.id.eq(banner.adminUsers.id))
                .where(banner.delYn.eq("N"))
                .fetchOne();
        return count;
    }

    @Transactional
    public void save(BannerMgController.SaveBanner saveBanner, HttpServletRequest request){

        HttpSession session = request.getSession(true);
        AdminUsers adminUsers = adminUserRepository.findById((Long) session.getAttribute("id")).get();

        //banner 테이블 저장
        Banner banner = Banner.builder()
                .id(saveBanner.getId())
                .bannerPath(saveBanner.getBannerPath())
                .adminUsers(adminUsers)
                .build();
        Long bannerId = bannerRepository.save(banner).getId();

        //기존에 등록된 파일 전부 삭제
        if(saveBanner.getId() != null){
            bannerFileRepository.deleteByBannerId(saveBanner.getId());
        }

        //banner_file 테이블 저장
        List<Long> fileIdxList = saveBanner.getFileIdxList();

        int index = 0;

        for (Long id : fileIdxList) {
            FileInfo fileInfo = fileRepository.findById(fileIdxList.get(index)).get();

            BannerFile bannerFile = BannerFile.builder()
                    .fileInfo(fileInfo)
                    .bannerId(bannerId)
                    .build();
            bannerFileRepository.save(bannerFile);
            index++;
        }
    }

    @Transactional
    public Long delete(Long id){
        Banner banner = bannerRepository.findById(id).get();
        banner.delete();
        return banner.getId();
    }
}
