package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.UserKnowledge;
import com.java.project.mapper.UserKnowledgeMapper;
import com.java.project.service.UserKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-10-29
 */
@Service
public class UserKnowledgeServiceImpl extends ServiceImpl<UserKnowledgeMapper, UserKnowledge> implements UserKnowledgeService {

    @Autowired
    UserKnowledgeMapper userKnowledgeMapper;

    @Override
    public Integer getUNum(int uid,int pid) {
        return userKnowledgeMapper.selectUNum(uid,pid);
    }
}
