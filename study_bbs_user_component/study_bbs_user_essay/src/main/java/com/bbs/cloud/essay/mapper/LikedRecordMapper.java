package com.bbs.cloud.essay.mapper;


import com.bbs.cloud.essay.dto.LikedRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface LikedRecordMapper {
    void insertLikedRecordDTO(LikedRecordDTO likedRecordDTO);
    LikedRecordDTO queryLikedRecordDTO(@Param("userId") String userId,@Param("essayId") String essayId);
    void updateLikedRecordDTO(LikedRecordDTO likedRecordDTO);
}
