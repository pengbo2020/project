package com.java.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.Image;
import com.java.project.entity.KnowledgeBasic;
import com.java.project.entity.KnowledgeDesc;
import com.java.project.mapper.ImageMapper;
import com.java.project.mapper.KnowledgeBasicMapper;
import com.java.project.mapper.KnowledgeDescMapper;
import com.java.project.service.KnowledgeBasicService;
import com.java.project.vo.KnowledgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 知识点 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class KnowledgeBasicServiceImpl extends ServiceImpl<KnowledgeBasicMapper, KnowledgeBasic> implements KnowledgeBasicService {

    @Autowired
    KnowledgeBasicMapper knowledgeBasicMapper;

    @Autowired
    KnowledgeDescMapper knowledgeDescMapper;

    @Autowired
    ImageMapper imageMapper;

    @Override
    public List<KnowledgeBasic> getQuestionKnowledge(Integer qid) {

        return knowledgeBasicMapper.selectQuestionKnowledge(qid);
    }

    @Override
    public List<KnowledgeVo> getKnowledge(Integer pn, Integer ps) {
        List<KnowledgeBasic> knowledgeBasics = knowledgeBasicMapper.getPage(pn*ps,ps);
        List<KnowledgeVo> knowledgeVos = new ArrayList<>();
        if(knowledgeBasics!=null) {
            knowledgeBasics.forEach((knowledgeBasic)->{
                List<KnowledgeDesc> knowledgeDescs = knowledgeDescMapper.selectList(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id", knowledgeBasic.getId()));
                Image audioFile = imageMapper.selectOne(new QueryWrapper<Image>().eq("pid", knowledgeBasic.getId()).eq("type", 1));
                knowledgeVos.add(new KnowledgeVo(knowledgeBasic,knowledgeDescs,audioFile));
            });
        }
        return knowledgeVos;
    }

    @Override
    public List<KnowledgeVo> getKnowledgeByChapterId(Integer cid, Integer pn, Integer ps) {

        List<KnowledgeBasic> knowledgeBasics = knowledgeBasicMapper.getPageByChapterId(cid,pn*ps,ps);
        List<KnowledgeVo> knowledgeVos = new ArrayList<>();
        if(knowledgeBasics!=null) {
            knowledgeBasics.forEach((knowledgeBasic)->{
                List<KnowledgeDesc> knowledgeDescs = knowledgeDescMapper.selectList(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id", knowledgeBasic.getId()));
                Image audioFile = imageMapper.selectOne(new QueryWrapper<Image>().eq("pid", knowledgeBasic.getId()).eq("type", 1));
                knowledgeVos.add(new KnowledgeVo(knowledgeBasic,knowledgeDescs,audioFile));
            });
        }
        return knowledgeVos;
    }

    @Override
    public List<KnowledgeVo> getCollectionKnowledgeByChapterId(Integer uid,  Integer cid, Integer pn, Integer ps) {
        List<KnowledgeBasic> knowledgeBasics = knowledgeBasicMapper.getCollectionPageByChapterId(uid,cid,pn*ps,ps);
        List<KnowledgeVo> knowledgeVos = new ArrayList<>();
        if(knowledgeBasics!=null) {
            knowledgeBasics.forEach((knowledgeBasic)->{
                List<KnowledgeDesc> knowledgeDescs = knowledgeDescMapper.selectList(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id", knowledgeBasic.getId()));
                Image audioFile = imageMapper.selectOne(new QueryWrapper<Image>().eq("pid", knowledgeBasic.getId()).eq("type", 1));
                knowledgeVos.add(new KnowledgeVo(knowledgeBasic,knowledgeDescs,audioFile));
            });
        }
        return knowledgeVos;
    }

}
