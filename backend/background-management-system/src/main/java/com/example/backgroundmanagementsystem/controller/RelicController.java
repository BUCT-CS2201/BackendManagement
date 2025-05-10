package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.pojo.vo.RelicVO;
import com.example.backgroundmanagementsystem.service.RelicService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relic")
@RequiredArgsConstructor
public class RelicController {
    private final RelicService relicService;

    /**
     * 加载用户列表
     * @param relicPageQueryDTO
     * @return
     */
    @PostMapping("/loadRelicList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadRelicList(RelicPageQueryDTO relicPageQueryDTO){
        return ResponseUtils.success(relicService.loadRelicList(relicPageQueryDTO));
    }

    /**
     * 添加或修改用户
     * @param relic
     * @return
     */
    @PostMapping("/addOrUpdateRelic")
    public ResponseVO addOrUpdateRelic(@Valid Relic relic){
        relicService.addOrUpdateRelic(relic);
        return ResponseUtils.success();
    }

    /**
     * 删除用户
     * @param relicId
     * @return
     */
    @PostMapping("/deleteRelic")
    public ResponseVO deleteRelic(@NotNull Long relicId){
        relicService.deleteRelic(relicId);
        return ResponseUtils.success();
    }
}
