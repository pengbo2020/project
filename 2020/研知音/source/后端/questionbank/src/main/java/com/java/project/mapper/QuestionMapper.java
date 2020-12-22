package com.java.project.mapper;

import com.java.project.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * <p>
 * 题目 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-05-24
 */
public interface QuestionMapper extends BaseMapper<Question> {

    List<Integer> selectAllSingleId();
    List<Integer> selectAllMultiId();
    List<Integer> selectAllAnalysisId();

    List<Question> selectQuestions(Integer eid);

    List<Question> selectQuestionsByType(@Param("uid") Integer uid, @Param("collect") Integer collect,@Param("wrong") Integer wrong,@Param("mark") Integer mark, @Param("pn") int pn, @Param("ps") Integer ps);

    List<Question> selectQuestionsByChapterId(Integer pid,Integer pn,Integer ps);

    List<Question> getHasDoneQuestionsByChapterId(Integer uid, Integer pid,Integer pn,Integer ps);

    List<Question> selectQuestionsByTypeAndChapterId(Integer uid, Integer cid, Integer collect, Integer wrong, Integer mark, int pn, Integer ps);
}
