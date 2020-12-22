package com.java.project.service.impl;

import com.java.project.entity.Exam;
import com.java.project.entity.ExamQuestion;
import com.java.project.entity.Question;
import com.java.project.mapper.ExamMapper;
import com.java.project.mapper.ExamQuestionMapper;
import com.java.project.mapper.QuestionMapper;
import com.java.project.service.ExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.utils.RandomRangeUtil;
import com.java.project.vo.QuestionNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 试卷 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-05-24
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamQuestionMapper examQuestionMapper;

    //事务方面  公共异常处理 自动填充
    //@Transactional()
    @Override

    @Transactional
    public Exam createExam(String id,Integer type) {
        //所有的单选题id
        List<Integer> allSingleId = questionMapper.selectAllSingleId();
        List<Integer> allMultiId = questionMapper.selectAllMultiId();
        List<Integer> allAnalysisId = questionMapper.selectAllAnalysisId();
        List<Integer> singleId =null;
        List<Integer> multiId = null;
        List<Integer> analyId =null;
        if(type==1) {
             singleId = RandomRangeUtil.getRandom(allSingleId.size(), 16);
             multiId = RandomRangeUtil.getRandom(allMultiId.size(), 17);
             analyId = RandomRangeUtil.getRandom(allAnalysisId.size(), 5);
        }else{
             singleId = RandomRangeUtil.getRandom(allSingleId.size(), 10);
             multiId = RandomRangeUtil.getRandom(allMultiId.size(), 5);
             analyId = new ArrayList<Integer>();
        }
        System.out.println("*******************第" + id + "次开始" + "********************");
        System.out.println(allSingleId);
        System.out.println(allMultiId);
        System.out.println(allAnalysisId);
        System.out.println(singleId);
        System.out.println(multiId);
        System.out.println(analyId);
        System.out.println("*******************第" + id + "次结束" + "********************");

        //把这些数据都放到数据库里面去

        Exam exam = new Exam(null, type, "快速练习","快速练习("+id+")", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        examMapper.insert(exam);
        Integer exam_id = exam.getId();
        for (int i = 0; i < 10; ++i) {
            examQuestionMapper.insert(new ExamQuestion(null, exam_id, allSingleId.get(singleId.get(i))));
            //System.out.println(allSingleId.get(singleId.get(i)));
        }
        for (int i = 0; i < 5; ++i) {
            examQuestionMapper.insert(new ExamQuestion(null, exam_id, allMultiId.get(multiId.get(i))));
           // System.out.println(allMultiId.get(multiId.get(i)));
        }

        /*for (int i = 0; i < 5; ++i) {
            examQuestionMapper.insert(new ExamQuestion(null, exam_id, allAnalysisId.get(analyId.get(i))));

        }*/
        return null;
    }

    @Override
    public Exam getExam(Integer uid,Integer type) {
        return examMapper.selectExam(uid,type);
    }

    @Override
    public List<Question> getQuestionsByExamIdAndType(Integer eid, Integer type) {
        return examMapper.getQuestionsByExamIdAndType(eid,type);

    }

    @Override
    public Exam getRandomOne(Integer type) {
        return examMapper.selectRandomOne(type);
    }

    @Override
    public List<Exam> getUserExams(Integer uid) {
        return examMapper.getUserExams(uid) ;
    }

    @Override
    public List<QuestionNum> getChaptersByPid(Integer eid, Integer pid) {
        return examMapper.selectChaptersByPid(eid,pid);
    }


}
