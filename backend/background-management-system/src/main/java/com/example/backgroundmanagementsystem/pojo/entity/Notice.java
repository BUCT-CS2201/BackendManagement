package com.example.backgroundmanagementsystem.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity{
    /**
     * 公告id
     */
    private Long noticeId;
    /**
     * 博物馆id
     */
    @NotNull
    private Long museumId;
    /**
     * 标题
     */
    @NotEmpty
    private String noticeTitle;
    /**
     * 作者
     */
    @NotEmpty
    private String noticeAuthor;
    /**
     * 发布内容
     */
    private String noticeContent;
    /**
     * 发布时间
     */
    private LocalDateTime noticeTime;
}
