package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseBRPageQueryDTO extends PageDTO{
    private Integer id;
    private String adminName;
    private String comment;
    private LocalDateTime backupTimeStart;
    private LocalDateTime backupTimeEnd;
}
