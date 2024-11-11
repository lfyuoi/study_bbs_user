package com.bbs.cloud.essay.mapper;

import com.bbs.cloud.essay.dto.PlayTourRecordDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PlayTourRecordMapper {
    void  insertPlayTourRecordMapper(PlayTourRecordDTO playTourRecordDTO);
}
