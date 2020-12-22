package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.UserKnowledge;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcj
 * @since 2020-10-29
 */
public interface UserKnowledgeService extends IService<UserKnowledge> {

    Integer getUNum(int uid,int pid);
}
