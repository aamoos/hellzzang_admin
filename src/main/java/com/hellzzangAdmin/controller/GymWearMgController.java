package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.dto.GymWearDto;
import com.hellzzangAdmin.dto.GymWearFileDto;
import com.hellzzangAdmin.entity.GymWear;
import com.hellzzangAdmin.repository.FileRepository;
import com.hellzzangAdmin.service.GymWearService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : gymWearController
 * author         : 김재성
 * date           : 2023-05-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-19        김재성       최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manage")
public class GymWearMgController {

    private final GymWearService gymWearService;
    private final FileRepository fileRepository;

    /**
     * @methodName : gymWear
     * @date : 2023-05-11 오전 10:47
     * @author : 김재성
     * @Description: 짐웨어 리스트
     **/
    @GetMapping("/gymWear/list")
    public String gymWear(String searchVal, @PageableDefault Pageable pageable, Model model){

        Page<GymWearDto> results = gymWearService.selectGymWearList(pageable, searchVal);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchVal", searchVal);
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());

        return "views/manage/gymWear/gymWear-list";
    }

    /**
     * @methodName : gymWearSave
     * @date : 2023-05-11 오후 3:52
     * @author : 김재성
     * @Description: 짐웨어 등록 화면
     **/
    @GetMapping("/gymWear/write")
    public String write(Model model){
        model.addAttribute("saveGymWear", new SaveGymWear());
        return "views/manage/gymWear/gymWear-write";
    }

    /**
    * @methodName : write
    * @date : 2023-05-24 오전 9:34
    * @author : 김재성
    * @Description: 짐웨어 등록 submit
    **/
    @PostMapping("/gymWear/write")
    public String write(@Valid SaveGymWear saveGymWear, BindingResult result, HttpServletRequest request){

        if (result.hasErrors()) {
            return "views/manage/gymWear/gymWear-write";
        }

        gymWearService.save(saveGymWear, request);

        return "redirect:/manage/gymWear/list";
    }

    /**
    * @methodName : update
    * @date : 2023-05-24 오전 9:59
    * @author : 김재성
    * @Description: 짐웨어 수정 화면
    **/
    @GetMapping("/gymWear/update/{id}")
    public String update(@PathVariable Long id, Model model){

        GymWear gymWear = gymWearService.find(id);

        //짐웨어 조회
        SaveGymWear saveGymWear = SaveGymWear.builder()
                .id(gymWear.getId())
                .title(gymWear.getTitle())
                .contents(gymWear.getContents())
                .thumbnailIdx(gymWear.getThumbnailIdx())
                .price(gymWear.getPrice())
                .build();

        //짐웨어 파일 리스트 조회
        List<GymWearFileDto> gymWearFile = gymWearService.findGymWearFileList(id);

        model.addAttribute("saveGymWear", saveGymWear);
        model.addAttribute("gymWearFile", gymWearFile);

        //썸네일이 등록된경우에만 model
        if(saveGymWear.thumbnailIdx != null){
            model.addAttribute("fileInfo", fileRepository.findById(saveGymWear.thumbnailIdx).get());
        }

        return "views/manage/gymWear/gymWear-update";
    }

    /**
    * @methodName : update
    * @date : 2023-05-24 오전 10:04
    * @author : 김재성
    * @Description: 짐웨어 수정 submit
    **/
    @PostMapping("/gymWear/update/{id}")
    public String update(@Valid SaveGymWear saveGymWear, BindingResult result, HttpServletRequest request){

        if (result.hasErrors()) {
            return "views/manage/gymWear/gymWear-update";
        }

        gymWearService.save(saveGymWear, request);

        return "redirect:/manage/gymWear/list";
    }

    /**
    * @methodName : gymWearDelete
    * @date : 2023-06-17 오전 12:00
    * @author : 김재성
    * @Description: 짐웨어 삭제 submit
    **/
    @PostMapping("/gymWear/delete")
    public String gymWearDelete(@RequestParam Long id) {
        gymWearService.delete(id);
        // 검증 통과 시, 사용자 등록 로직 수행
        return "redirect:/manage/gymWear/list";
    }

    /**
    * @methodName :
    * @date : 2023-06-17 오전 12:00
    * @author : 김재성
    * @Description: 짐웨어 저장 dto
    **/
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveGymWear{

        private Long id;

        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @Lob
        @Column(name = "text_area", columnDefinition = "CLOB")
        @NotBlank(message = "내용을 입력해주세요.")
        private String contents;

        private Long thumbnailIdx;

        private List<Long> contentFileIdx;

        @NotNull(message = "가격은 0원 보다 크게 작성해야 합니다.")
        @Min(value = 1, message = "가격은 0원 보다 크게 작성해야 합니다.")
        private Long price;     //가격

        private List<String> options;

        @Builder
        public SaveGymWear(Long id, String title, String contents, Long thumbnailIdx, List<Long> contentFileIdx, Long price, List<String> options){
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.thumbnailIdx = thumbnailIdx;
            this.contentFileIdx = contentFileIdx;
            this.price = price;
            this.options = options;
        }
    }
}
