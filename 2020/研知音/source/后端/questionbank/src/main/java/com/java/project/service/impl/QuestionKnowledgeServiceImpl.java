package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.QuestionKnowledge;
import com.java.project.mapper.QuestionKnowledgeMapper;
import com.java.project.service.QuestionKnowledgeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问题与知识点的关联 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class QuestionKnowledgeServiceImpl extends ServiceImpl<QuestionKnowledgeMapper, QuestionKnowledge> implements QuestionKnowledgeService {

}
