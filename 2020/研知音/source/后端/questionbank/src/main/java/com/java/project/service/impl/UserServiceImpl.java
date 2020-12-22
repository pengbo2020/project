package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.User;
import com.java.project.mapper.UserMapper;
import com.java.project.service.UserService;
import com.java.project.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<DataVo> getTotalUserQuestionNum(Integer uid, Integer pid) {
        return userMapper.selectTotalUserQuestionNum(uid,pid);
    }

    @Override
    public List<DataVo> getQuestionNumByUid(Integer uid, Integer pid) {
        return userMapper.selectQuestionNumByUid(uid,pid);
    }
}
