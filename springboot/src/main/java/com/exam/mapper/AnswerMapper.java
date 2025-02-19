package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AnswerMapper {
    @Select("select question, subject, score, section,level, 1 as type from multi_question " +
            "union select  question, subject, score, section,level,2 as type from judge_question " +
            "union select  question, subject, score, section,level,3 as type from fill_question")
    IPage<AnswerVO> findAll(Page page);
}