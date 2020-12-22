package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.UserQuestion;
import com.java.project.mapper.UserQuestionMapper;
import com.java.project.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户做过的题目，是有答案的 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements UserQuestionService {

    @Autowired
    UserQuestionMapper userQuestionMapper;

    @Override
    public void removeByExamId(Integer uid, Integer eid) {
        userQuestionMapper.deleteByExamId(uid,eid);
    }
}
