package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.KnowledgeBasic;
import com.java.project.vo.KnowledgeVo;

import java.util.List;

/**
 * <p>
 * 知识点 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface KnowledgeBasicService extends IService<KnowledgeBasic> {

    List<KnowledgeBasic> getQuestionKnowledge(Integer qid);


    List<KnowledgeVo> getKnowledge(Integer pn, Integer ps);

    List<KnowledgeVo> getKnowledgeByChapterId(Integer cid, Integer pn, Integer ps);

    List<KnowledgeVo> getCollectionKnowledgeByChapterId(Integer uid,Integer cid, Integer pn, Integer ps);
}
