package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.User;
import com.java.project.vo.DataVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-24
 */
public interface UserService extends IService<User> {


    List<DataVo> getTotalUserQuestionNum(Integer uid, Integer pid);

    List<DataVo> getQuestionNumByUid(Integer uid, Integer pid);
}
