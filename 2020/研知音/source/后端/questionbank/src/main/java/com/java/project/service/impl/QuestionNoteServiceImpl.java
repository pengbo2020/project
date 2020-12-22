package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.QuestionNote;
import com.java.project.mapper.QuestionNoteMapper;
import com.java.project.service.QuestionNoteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 做的题的笔记 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class QuestionNoteServiceImpl extends ServiceImpl<QuestionNoteMapper, QuestionNote> implements QuestionNoteService {

}
