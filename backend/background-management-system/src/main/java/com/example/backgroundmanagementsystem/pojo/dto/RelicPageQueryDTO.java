package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelicPageQueryDTO extends PageDTO{
    private Long relicId;
    private Long museumId;
    private String name;
    private String type;
    private String matrials;
    private String dynasty;
    private String author;
    private Integer entryTime;
}
