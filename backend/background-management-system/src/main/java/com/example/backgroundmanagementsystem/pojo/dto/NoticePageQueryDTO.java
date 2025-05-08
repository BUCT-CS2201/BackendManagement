package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticePageQueryDTO extends PageDTO{
    private Long notice_id;
    private String title;
    private String name;
    private String content;
    private String time;
    private Integer ispublic;
}
