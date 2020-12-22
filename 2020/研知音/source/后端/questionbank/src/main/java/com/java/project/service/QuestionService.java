package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.Question;

import java.util.List;

/**
 * <p>
 * 题目 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface QuestionService extends IService<Question> {

    List<Question> getQuestions(Integer eid);

    List<Question> getQuestionsByType(Integer uid, Integer collect, Integer wrong, Integer mark, Integer pn, Integer ps);

    List<Question> getQuestionsByChapterId(Integer pid,Integer pn,Integer ps);

    List<Question> getHasDoneQuestionsByChapterId(Integer uid, Integer pid,Integer pn,Integer ps);

    List<Question> getQuestionsByTypeAndChapterId(Integer uid, Integer cid, Integer collect, Integer wrong, Integer mark, Integer pn, Integer ps);
}
