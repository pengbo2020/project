package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.Chapter;
import com.java.project.vo.ChapterVo;
import com.java.project.vo.DataVo;
import com.java.project.vo.QuestionNum;

import java.util.List;

/**
 * <p>
 * 章节 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapters(Integer uid, Integer pid);

    List<Chapter> getWrongQuestionChapter(Integer uid, Integer pid);

    List<Chapter> getCollectQuestionChapter(Integer uid, Integer pid);

    List<Chapter> getMarkQuestionChapter(Integer uid, Integer pid);


    Integer getChapterQuestionNum(Integer uid, Integer cid, Integer wrong, Integer collect, Integer mark);


    List<DataVo> getTotalChapterQuestionNum(Integer pid);

    List<DataVo> getQuestionNumByPid(Integer pid);


    List<QuestionNum> getUserChapterByType(Integer uid, Integer pid, Integer collect, Integer wrong, Integer mark);

    List<DataVo> getCidAndQid(Integer eid);
}
