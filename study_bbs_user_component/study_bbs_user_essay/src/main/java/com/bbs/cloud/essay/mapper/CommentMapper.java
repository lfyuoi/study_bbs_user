package com.bbs.cloud.essay.mapper;

import com.bbs.cloud.essay.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CommentMapper {

    void insertCommentDOT(CommentDTO commentDTO);

    CommentDTO queryCommentDTO(@Param("id") String id);
}
