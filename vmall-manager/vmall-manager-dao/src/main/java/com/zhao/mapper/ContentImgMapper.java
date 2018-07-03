package com.zhao.mapper;

import com.zhao.pojo.ContentImg;
import com.zhao.pojo.ContentImgExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentImgMapper {
    long countByExample(ContentImgExample example);

    int deleteByExample(ContentImgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ContentImg record);

    int insertSelective(ContentImg record);

    List<ContentImg> selectByExample(ContentImgExample example);

    ContentImg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ContentImg record, @Param("example") ContentImgExample example);

    int updateByExample(@Param("record") ContentImg record, @Param("example") ContentImgExample example);

    int updateByPrimaryKeySelective(ContentImg record);

    int updateByPrimaryKey(ContentImg record);
}