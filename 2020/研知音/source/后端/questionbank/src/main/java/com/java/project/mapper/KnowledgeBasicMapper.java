package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.KnowledgeBasic;

import java.util.List;

/**
 * <p>
 * 知识点 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface KnowledgeBasicMapper extends BaseMapper<KnowledgeBasic> {

    List<KnowledgeBasic> selectQuestionKnowledge(Integer qid);

    List<KnowledgeBasic> getPage(int offset, Integer ps);

    List<KnowledgeBasic> getPageByChapterId(Integer cid, Integer pn, Integer ps);

    List<KnowledgeBasic> getCollectionPageByChapterId(Integer uid , Integer cid, int pn, Integer ps);
}
