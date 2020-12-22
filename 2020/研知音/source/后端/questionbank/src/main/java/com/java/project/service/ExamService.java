package com.java.project.service;

import com.java.project.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.Question;
import com.java.project.vo.QuestionNum;

import java.util.List;

/**
 * <p>
 * 试卷 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-05-24
 */
public interface ExamService extends IService<Exam> {

    Exam createExam(String id,Integer type);

    Exam getExam(Integer uid,Integer type);

    List<Question> getQuestionsByExamIdAndType(Integer eid, Integer type);

    Exam getRandomOne(Integer type);

    List<Exam> getUserExams(Integer uid);

    List<QuestionNum> getChaptersByPid(Integer eid, Integer pid);
}
