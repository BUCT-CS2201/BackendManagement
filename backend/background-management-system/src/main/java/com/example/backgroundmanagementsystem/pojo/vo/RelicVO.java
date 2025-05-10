package com.example.backgroundmanagementsystem.pojo.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelicVO {
    /**
     * 文物id
     */
    private Long relicId;
    /**
     * 博物馆id
     */
    @NotEmpty
    private Long museumId;
    /**
     * 文物名
     */
    private String name;
    /**
     * 文物种类
     */
    @NotEmpty
    private String type;
    /**
     * 文物描述
     */
    private String description;
    /**
     * 文物尺寸
     */
    private String size;
    /**
     * 文物材质
     */
    private String materials;
    /**
     * 文物年代
     */
    private String dynasty;
    /**
     * 点赞数
     */
    private Integer likesCount;
    /**
     * 浏览次数
     */
    private Integer viewsCount;
    /**
     * 文物作者
     */
    private String author;
    /**
     * 文物入库时间
     */
    private Integer entryTime;
}
