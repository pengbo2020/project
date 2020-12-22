package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.Chapter;
import com.java.project.entity.ChapterKnowledge;

import java.util.List;

/**
 * <p>
 * 章节和知识点 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface ChapterKnowledgeMapper extends BaseMapper<ChapterKnowledge> {

    int selectUserCount(Integer uid, Integer cid);

    Integer selectKNum(int pid);

    List<Chapter> selectCollectionChapter(Integer uid, Integer pid);

    List<Chapter> selectCollectionChildChapter(Integer uid, Integer pid);

    int selectCollectionUserCount(Integer uid, Integer cid);
}
