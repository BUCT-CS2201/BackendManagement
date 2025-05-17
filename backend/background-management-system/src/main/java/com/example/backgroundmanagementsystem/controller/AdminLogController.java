package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogAddDTO;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.AdminLogService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class AdminLogController {
    private final AdminLogService adminLogService;

    @PostMapping("/loadLogList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadLogList(AdminLogPageQueryDTO dto){
        PageResultVO ans = adminLogService.loadLogList(dto);
        return ResponseUtils.success(ans);
    }

    /**
     * 删除日志
     * @param logId
     * @return
     */
    @PostMapping("/deleteLog")
    public ResponseVO deleteLog(@NotNull Long logId){
        adminLogService.deleteLog(logId, BaseContext.getUserToken().getName());
        return ResponseUtils.success();
    }

    /**
     * 增加日志
     * @param dto
     * @return
     */
    @PostMapping("/addLog")
    public ResponseVO addLog(@Valid AdminLogAddDTO dto){
        adminLogService.addLog(dto);
        return ResponseUtils.success();
    }
}
