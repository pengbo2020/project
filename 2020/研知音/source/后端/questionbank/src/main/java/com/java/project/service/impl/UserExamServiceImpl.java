package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.UserExam;
import com.java.project.mapper.UserExamMapper;
import com.java.project.service.UserExamService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户做过的试卷 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class UserExamServiceImpl extends ServiceImpl<UserExamMapper, UserExam> implements UserExamService {

}
