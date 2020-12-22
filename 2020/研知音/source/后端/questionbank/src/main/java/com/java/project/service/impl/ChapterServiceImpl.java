package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.Chapter;
import com.java.project.mapper.ChapterMapper;
import com.java.project.service.ChapterService;
import com.java.project.vo.ChapterVo;
import com.java.project.vo.DataVo;
import com.java.project.vo.QuestionNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 章节 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {


    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public List<ChapterVo> getChapters(Integer uid, Integer pid) {
        return chapterMapper.selectChapters(uid,pid);
    }

    @Override
    public List<Chapter> getWrongQuestionChapter(Integer uid, Integer pid) {
        return chapterMapper.selectWrongQuestionChapter(uid,pid);
    }

    @Override
    public List<Chapter> getCollectQuestionChapter(Integer uid, Integer pid) {
        return chapterMapper.selectCollectQuestionChapter(uid,pid);
    }

    @Override
    public List<Chapter> getMarkQuestionChapter(Integer uid, Integer pid) {
        return chapterMapper.selectMarkQuestionChapter(uid,pid);
    }

    @Override
    public Integer getChapterQuestionNum(Integer uid, Integer cid, Integer wrong, Integer collect, Integer mark) {
        return chapterMapper.selectChapterQuestionNum(uid,cid,wrong,collect,mark);
    }

    @Override
    public List<DataVo> getTotalChapterQuestionNum(Integer pid) {
        return  chapterMapper.selectTotalChapterQuestionNum(pid);
    }

    @Override
    public List<DataVo> getQuestionNumByPid(Integer pid) {
        return  chapterMapper.selectQuestionNumByPid(pid);
    }

    @Override
    public List<QuestionNum> getUserChapterByType(Integer uid, Integer pid, Integer collect, Integer wrong, Integer mark) {
        return chapterMapper.selectUserChapterByType(uid,pid,collect,wrong,mark);
    }

    @Override
    public List<DataVo> getCidAndQid(Integer eid) {
        return chapterMapper.selectCidAndQid(eid);
    }


}
