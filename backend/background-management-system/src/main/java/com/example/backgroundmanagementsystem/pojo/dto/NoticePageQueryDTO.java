package com.example.backgroundmanagementsystem.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticePageQueryDTO extends PageDTO{
    private Long noticeId;
    /**
     * 博物馆id
     */
    private Long museumId;
    /**
     * 标题
     */
    private String noticeTitle;
    /**
     * 作者
     */
    private String noticeAuthor;
    private LocalDateTime noticeTimeStart;

    private LocalDateTime noticeTimeEnd;
}
