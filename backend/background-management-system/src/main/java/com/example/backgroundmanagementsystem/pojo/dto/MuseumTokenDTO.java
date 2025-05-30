package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MuseumTokenDTO {
    /**
     * 博物馆id
     */
    private Long museumId;
    /**
     * 博物馆名称
     */
    private String museumName;
    /**
     * 博物馆介绍
     */
    private String description;
    /**
     * 博物馆地址
     */
    private String address;
    /**
     * 博物馆网址
     */
    private String websiteUrl;
    /**
     * 博物馆购票网址
     */
    private String bookingUrl;
    /**
     * token
     */
    private String token;
}
