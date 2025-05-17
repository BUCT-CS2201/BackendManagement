package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.DataBaseService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/database")
@RequiredArgsConstructor
public class DataBaseController {
    private final DataBaseService dataBaseService;

    @PostMapping("/loadDataBaseList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO){
        return ResponseUtils.success(dataBaseService.loadDataBaseList(dataBaseBRPageQueryDTO));
    }

    @PostMapping("/backup")
    public ResponseVO backup(String comment){
        dataBaseService.backup(comment);
        return ResponseUtils.success();
    }
}
