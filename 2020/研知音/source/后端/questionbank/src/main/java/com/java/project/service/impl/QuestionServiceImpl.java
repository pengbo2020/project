package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.Question;
import com.java.project.mapper.QuestionMapper;
import com.java.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestions(Integer eid) {
        return questionMapper.selectQuestions(eid);

    }

    @Override
    public List<Question> getQuestionsByType(Integer uid, Integer collect, Integer wrong, Integer mark, Integer pn, Integer ps) {
        pn=(ps*pn);
        return questionMapper.selectQuestionsByType(uid,collect,wrong,mark,pn,ps);
    }

    @Override
    public List<Question> getQuestionsByChapterId(Integer pid,Integer pn,Integer ps) {

        return questionMapper.selectQuestionsByChapterId(pid,(pn*ps),ps);
    }

    @Override
    public List<Question> getHasDoneQuestionsByChapterId(Integer uid, Integer pid, Integer pn, Integer ps) {
        return questionMapper.getHasDoneQuestionsByChapterId(uid,pid,pn*ps,ps);
    }

    @Override
    public List<Question> getQuestionsByTypeAndChapterId(Integer uid, Integer cid, Integer collect, Integer wrong, Integer mark, Integer pn, Integer ps) {
        return questionMapper.selectQuestionsByTypeAndChapterId(uid,cid,collect,wrong,mark,pn*ps,ps);
    }

    /*@Override
    public List<Question> getHasDoneQuestionsByChapterId(Integer pid, Integer uid) {
        return questionMapper.getHasDoneQuestionsByChapterId(pid,uid);
    }*/


}
