package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.DataBaseBR;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.DataBaseService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/database")
@RequiredArgsConstructor
public class DataBaseController {
    private final DataBaseService dataBaseService;

    /**
     * 加载备份列表
     * @param dataBaseBRPageQueryDTO
     * @return
     */
    @PostMapping("/loadDataBaseList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO){
        return ResponseUtils.success(dataBaseService.loadDataBaseList(dataBaseBRPageQueryDTO));
    }

    /**
     * 添加或修改数据库备份
     * @param dataBaseBR
     * @return
     */
    @PostMapping("/addOrUpdateBackup")
    public ResponseVO addOrUpdateBackup(DataBaseBR dataBaseBR){
        dataBaseService.addOrUpdateBackup(dataBaseBR);
        return ResponseUtils.success();
    }

    /**
     * 删除备份记录
     * @param id
     * @return
     */
    @PostMapping("/deleteDataBaseBackup")
    public ResponseVO deleteDataBaseBackup(@NotNull Integer id){
        dataBaseService.deleteDataBaseBackup(id);
        return ResponseUtils.success();
    }

    /**
     * 获取默认备份路径
     * @return
     */
    @PostMapping("/getDefaultPath")
    public ResponseVO<String> getDefaultPath(){
        return ResponseUtils.success(dataBaseService.getDefaultPath());
    }
}
