package com.example.backgroundmanagementsystem.controller;

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
    @PostMapping("/backup")
    public ResponseVO test(){
        dataBaseService.test();
        return ResponseUtils.success();
    }
}
