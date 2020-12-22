package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.UserNote;
import com.java.project.mapper.UserNoteMapper;
import com.java.project.service.UserNoteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户笔记 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class UserNoteServiceImpl extends ServiceImpl<UserNoteMapper, UserNote> implements UserNoteService {

}
