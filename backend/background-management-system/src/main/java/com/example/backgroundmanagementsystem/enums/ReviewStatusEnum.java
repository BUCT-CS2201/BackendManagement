package com.example.backgroundmanagementsystem.enums;

/**
 * 审核状态枚举
 */
public enum ReviewStatusEnum {
    PENDING(0,"审核中"),
    APPROVED(1,"过审"),
    REJECTED(2,"未过审");

    private Integer status;
    private String description;

    ReviewStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public static ReviewStatusEnum getByStatus(Integer status){
        for (ReviewStatusEnum reviewStatusEnum : ReviewStatusEnum.values()) {
            if(reviewStatusEnum.getStatus().equals(status)){
                return reviewStatusEnum;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
