package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.Chapter;
import com.java.project.entity.ChapterKnowledge;
import com.java.project.mapper.ChapterKnowledgeMapper;
import com.java.project.service.ChapterKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 章节和知识点 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class ChapterKnowledgeServiceImpl extends ServiceImpl<ChapterKnowledgeMapper, ChapterKnowledge> implements ChapterKnowledgeService {

    @Autowired
    ChapterKnowledgeMapper chapterKnowledgeMapper;

    @Override
    public int getUserCount(Integer uid,Integer cid) {
        return  chapterKnowledgeMapper.selectUserCount(uid,cid);
    }

    @Override
    public Integer getKNum(int pid) {
        return  chapterKnowledgeMapper.selectKNum(pid);
    }

    @Override
    public List<Chapter> getCollectionChapter(Integer uid, Integer pid) {
        return chapterKnowledgeMapper.selectCollectionChapter(uid,pid);
    }

    @Override
    public List<Chapter> getCollectionChildChapter(Integer uid, Integer pid) {
        return chapterKnowledgeMapper.selectCollectionChildChapter(uid,pid);
    }

    @Override
    public int getCollectionUserCount(Integer uid, Integer cid) {
        return chapterKnowledgeMapper.selectCollectionUserCount(uid,cid);
    }
}
