package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.Chapter;
import com.java.project.entity.ChapterKnowledge;

import java.util.List;

/**
 * <p>
 * 章节和知识点 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface ChapterKnowledgeService extends IService<ChapterKnowledge> {

    int getUserCount(Integer uid,Integer cid);

    Integer getKNum(int pid);

    List<Chapter> getCollectionChapter(Integer uid, Integer pid);

    List<Chapter> getCollectionChildChapter(Integer uid, Integer pid);

    int getCollectionUserCount(Integer uid, Integer cid);
}
