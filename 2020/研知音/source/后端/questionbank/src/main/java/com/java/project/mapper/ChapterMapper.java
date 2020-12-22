package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.Chapter;
import com.java.project.vo.ChapterVo;
import com.java.project.vo.DataVo;
import com.java.project.vo.QuestionNum;

import java.util.List;

/**
 * <p>
 * 章节 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface ChapterMapper extends BaseMapper<Chapter> {

    List<ChapterVo> selectChapters(Integer uid, Integer pid);

    List<Chapter> selectWrongQuestionChapter(Integer uid, Integer pid);

    List<Chapter> selectCollectQuestionChapter(Integer uid, Integer pid);

    List<Chapter> selectMarkQuestionChapter(Integer uid, Integer pid);

    Integer selectChapterQuestionNum(Integer uid, Integer cid, Integer wrong, Integer collect, Integer mark);

    List<DataVo> selectTotalChapterQuestionNum(Integer pid);

    List<DataVo> selectQuestionNumByPid(Integer pid);

    List<QuestionNum> selectUserChapterByType(Integer uid, Integer pid, Integer collect, Integer wrong, Integer mark);

    List<DataVo> selectCidAndQid(Integer eid);
}
