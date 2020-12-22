package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.UserQuestion;

/**
 * <p>
 * 用户做过的题目，是有答案的 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface UserQuestionService extends IService<UserQuestion> {

    void removeByExamId(Integer uid, Integer eid);
}
