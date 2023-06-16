package com.hellzzangAdmin.controller;

import com.hellzzangAdmin.entity.FileInfo;
import com.hellzzangAdmin.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * packageName    : com.hellzzangAdmin.controller
 * fileName       : FileController
 * author         : 김재성
 * date           : 2023-05-11
 * description    : 파일 공통 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-11        김재성       최초 생성
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
    * @methodName : fileUpload
    * @date : 2023-06-16 오후 11:58
    * @author : 김재성
    * @Description: 파일 단일 업로드
    **/
    @PostMapping("/file-upload")
    @ResponseBody
    public FileInfo fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
        return fileService.uploadFile(request, file);
    }

    /**
    * @methodName : multiFileUpload
    * @date : 2023-06-16 오후 11:58
    * @author : 김재성
    * @Description: 멀티 파일 업로드
    **/
    @PostMapping(value={"/multi-file-upload"})
    @ResponseBody
    public List<FileInfo> multiFileUpload(
            @RequestParam(value = "article_file", required = false) List<MultipartFile> multipartFile
            , HttpServletRequest request) throws IOException {
        return fileService.MultiUploadFile(request, multipartFile);
    }
    
    /**
    * @methodName : deleteFile
    * @date : 2023-05-11 오후 5:14
    * @author : 김재성
    * @Description: 파일 삭제
    **/
    @ResponseBody
    @PostMapping("/deleteFile")
    public Long deleteFile(@RequestBody Long fileId) {
        return fileService.deleteFile(fileId);
    }

    /**
    * @methodName : downloadFile
    * @date : 2023-06-16 오후 11:59
    * @author : 김재성
    * @Description: 파일 다운로드
    **/
    @GetMapping(value = {"/download/{fileIdx}"})
    @ResponseBody
    public void downloadFile(HttpServletResponse res, @PathVariable("fileIdx") Long fileIdx) throws Exception {
        fileService.downloadFile(res, fileIdx);
    }

    /**
    * @methodName : getFile
    * @date : 2023-06-16 오후 11:59
    * @author : 김재성
    * @Description: 파일 썸네일
    **/
    @ResponseBody
    @GetMapping("/thumbnail/{fileIdx}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileIdx") Long fileIdx) throws Exception {
        return fileService.getThumbnail(fileIdx);
    }
}
