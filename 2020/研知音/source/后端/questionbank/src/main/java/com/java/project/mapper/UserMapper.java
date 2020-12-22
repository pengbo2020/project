package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.User;
import com.java.project.vo.DataVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-06-24
 */
public interface UserMapper extends BaseMapper<User> {

    List<DataVo> selectTotalUserQuestionNum(Integer uid, Integer pid);

    List<DataVo> selectQuestionNumByUid(Integer uid, Integer pid);
}
