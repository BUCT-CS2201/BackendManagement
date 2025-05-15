package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.RelicService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relic")
@RequiredArgsConstructor
public class RelicController {
    private final RelicService relicService;

    /**
     * 加载藏品
     * @param relicPageQueryDTO
     * @return
     */
    @PostMapping("/loadRelicList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadRelicList(RelicPageQueryDTO relicPageQueryDTO){
        return ResponseUtils.success(relicService.loadRelicList(relicPageQueryDTO));
    }

    /**
     * 添加或修改藏品
     * @param cultural_relic
     * @return
     */
    @PostMapping("/addOrUpdateRelic")
    public ResponseVO addOrUpdateRelic(@Valid Relic cultural_relic,String adminName){
        relicService.addOrUpdateRelic(cultural_relic,adminName);
        return ResponseUtils.success();
    }

    /**
     * 删除藏品
     * @param relicId
     * @return
     */
    @PostMapping("/deleteRelic")
    public ResponseVO deleteRelic(@NotNull Long relicId,String adminName){
        relicService.deleteRelic(relicId,adminName);
        return ResponseUtils.success();
    }
}
