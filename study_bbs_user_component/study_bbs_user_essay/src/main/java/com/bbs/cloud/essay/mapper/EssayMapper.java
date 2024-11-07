package com.bbs.cloud.essay.mapper;

import com.bbs.cloud.essay.dto.EssayDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface EssayMapper {
    void insertEssayDTO(EssayDTO essayDTO);

    EssayDTO queryEssayDTO(@Param("id") String id);

    void updateEssayDTO(EssayDTO essayDTO);
}
