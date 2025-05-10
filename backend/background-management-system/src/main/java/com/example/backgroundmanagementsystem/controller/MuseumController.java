package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.MuseumPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Museum;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.MuseumService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/museum")
@RequiredArgsConstructor
public class MuseumController {
    private final MuseumService museumService;

    /**
     * 加载博物馆列表
     * @param museumPageQueryDTO
     * @return
     */
    @PostMapping("/loadMuseumList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadMuseumList(MuseumPageQueryDTO museumPageQueryDTO){
        return ResponseUtils.success(museumService.loadMuseumList(museumPageQueryDTO));
    }

    /**
     * 添加或修改博物馆
     * @param museum
     * @return
     */
    @PostMapping("/addOrUpdateMuseum")
    public ResponseVO addOrUpdateMuseum(@Valid Museum museum){
        museumService.addOrUpdateMuseum(museum);
        return ResponseUtils.success();
    }

    /**
     * 删除博物馆
     * @param museumId
     * @return
     */
    @PostMapping("/deleteMuseum")
    public ResponseVO deleteMuseum(@NotNull Long museumId){
        museumService.deleteMuseum(museumId);
        return ResponseUtils.success();
    }
}