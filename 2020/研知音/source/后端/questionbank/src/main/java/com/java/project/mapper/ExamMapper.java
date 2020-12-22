package com.java.project.mapper;

import com.java.project.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.Question;
import com.java.project.vo.QuestionNum;

import java.util.List;

/**
 * <p>
 * 试卷 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-05-24
 */
public interface ExamMapper extends BaseMapper<Exam> {

    Exam selectExam(Integer uid,Integer type);

    List<Question> getQuestionsByExamIdAndType(Integer eid, Integer type);

    Exam selectRandomOne(Integer type);

    List<Exam> getUserExams(Integer uid);

    List<QuestionNum> selectChaptersByPid(Integer eid, Integer pid);
}
