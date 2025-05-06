package com.example.backgroundmanagementsystem.enums;

public enum UserCommentStatusEnum {
    DISABLE(0,"禁止评论"),
    ENABLE(1,"允许评论");
    private Integer status;
    private String description;
    UserCommentStatusEnum(Integer status, String description){
        this.status = status;
        this.description = description;
    }

    public static UserCommentStatusEnum getByStatus(Integer status){
        for (UserCommentStatusEnum value : UserCommentStatusEnum.values()) {
            if (value.getStatus().equals(status)){
                return value;
            }
        }
        return null;
    }

    public Integer getStatus(){
        return status;
    }
    public String getDescription(){
        return description;
    }
}
