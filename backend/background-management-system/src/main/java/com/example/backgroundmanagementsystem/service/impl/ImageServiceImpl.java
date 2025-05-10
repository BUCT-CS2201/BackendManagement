package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.ReviewStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.CommentMapper;
import com.example.backgroundmanagementsystem.mapper.ImageMapper;
import com.example.backgroundmanagementsystem.pojo.dto.UserImagePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserImageStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
import com.example.backgroundmanagementsystem.pojo.entity.UserImage;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.UserImageVO;
import com.example.backgroundmanagementsystem.service.ImageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageMapper imageMapper;
    private final CommentMapper commentMapper;
    @Override
    public PageResultVO loadImageList(UserImagePageQueryDTO userImagePageQueryDTO) {
        log.info("图片分页查询：{}",userImagePageQueryDTO);
        PageHelper.startPage(userImagePageQueryDTO.getPageNo(), userImagePageQueryDTO.getPageSize());
        Page<UserImageVO> page = imageMapper.findBatch(userImagePageQueryDTO);
        return new PageResultVO(userImagePageQueryDTO.getPageNo(), userImagePageQueryDTO.getPageSize(), page.getTotal(),page.getResult());
    }

    @Override
    public void updateImageStatus(UserImageStatusUpdateDTO userImageStatusUpdateDTO) {
        log.info("修改图片审核状态：{}",userImageStatusUpdateDTO);
        Long imageId = userImageStatusUpdateDTO.getImageId();
        Long commentId = userImageStatusUpdateDTO.getCommentId();
        Integer status = userImageStatusUpdateDTO.getStatus();

        if(!ArrayUtils.contains(new int[]{ReviewStatusEnum.PENDING.getStatus(), ReviewStatusEnum.APPROVED.getStatus(), ReviewStatusEnum.REJECTED.getStatus()}, status)){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        // 关联评论被删除
        Comment comment = commentMapper.findByCommentId(commentId);
        if(null==comment){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该图片关联的评论已被删除");
        }
        // 关联评论未过审
        if(ReviewStatusEnum.APPROVED.getStatus().equals(status) && !ReviewStatusEnum.APPROVED.getStatus().equals(comment.getStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该图片关联的评论未过审");
        }
        // 修改图片状态
        UserImage userImage = new UserImage();
        userImage.setImageId(imageId);
        userImage.setStatus(status);
        imageMapper.updateStatusByImageId(userImage);
    }

    @Override
    public void loadImage(String imageName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
    }
}
