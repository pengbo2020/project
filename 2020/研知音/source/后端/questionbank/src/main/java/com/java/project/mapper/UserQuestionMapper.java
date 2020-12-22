package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.UserQuestion;

/**
 * <p>
 * 用户做过的题目，是有答案的 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface UserQuestionMapper extends BaseMapper<UserQuestion> {

    void deleteByExamId(Integer uid, Integer eid);
}
