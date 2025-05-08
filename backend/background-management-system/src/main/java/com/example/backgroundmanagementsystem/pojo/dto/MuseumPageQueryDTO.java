package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MuseumPageQueryDTO extends PageDTO{
    private Long museumId;
    private String museumName;
    private String description;
    private String address;
    private String websiteUrl;
    private String bookingUrl;
}
