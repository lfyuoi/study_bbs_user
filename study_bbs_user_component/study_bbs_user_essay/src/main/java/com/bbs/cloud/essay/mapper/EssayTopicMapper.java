package com.bbs.cloud.essay.mapper;

import com.bbs.cloud.essay.dto.EssayTopicDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface EssayTopicMapper {
    void insertEssayTopicDTO(@Param("data") List<EssayTopicDTO> essayTopicDTOS);
}
