package com.bbs.cloud.user.mapper;

import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.dto.ScoreConsumeRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ScoreConsumeRecordMapper {

    void insertScoreConsumeRecord(ScoreConsumeRecordDTO scoreConsumeRecordDTO);

    ScoreCardDTO queryScoreConsumeRecord(@Param("userId") String userId);

}
