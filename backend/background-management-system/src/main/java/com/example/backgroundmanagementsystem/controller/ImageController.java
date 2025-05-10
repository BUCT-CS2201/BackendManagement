package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.UserImagePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserImageStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.ImageService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    /**
     * 分页查询图片列表
     * @param userImagePageQueryDTO
     * @return
     */
    @PostMapping("/loadImageList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadImageList(UserImagePageQueryDTO userImagePageQueryDTO){
        return ResponseUtils.success(imageService.loadImageList(userImagePageQueryDTO));
    }

    /**
     * 修改图片审核状态
     * @param userImageStatusUpdateDTO
     * @return
     */
    @PostMapping("/updateImageStatus")
    public ResponseVO updateImageStatus(@Valid UserImageStatusUpdateDTO userImageStatusUpdateDTO){
        imageService.updateImageStatus(userImageStatusUpdateDTO);
        return ResponseUtils.success();
    }

    /**
     * 加载图片
     * @param imageName
     */
    @GetMapping("/loadImage")
    public void loadImage(String imageName){
        imageService.loadImage(imageName);
    }

}
