package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.UserKnowledge;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-10-29
 */
public interface UserKnowledgeMapper extends BaseMapper<UserKnowledge> {

    Integer selectUNum(int uid,int pid);
}
