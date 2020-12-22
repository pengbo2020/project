package com.java.project.controller;

import com.java.project.entity.Chapter;
import com.java.project.entity.ChapterQuestion;
import com.java.project.entity.Question;
import com.java.project.service.ChapterQuestionService;
import com.java.project.service.ChapterService;
import com.java.project.service.ExamService;
import com.java.project.service.QuestionService;
import com.java.project.utils.Result;
import com.java.project.vo.AddQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @Autowired
    ChapterQuestionService chapterQuestionService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    ExamService examService;



    @RequestMapping("index")
    public  String index()
    {
        return "index";
    }

    /**
     * pid:1,
     *                 id:6,
     *                 type:1,
     *
     * **/
    @ResponseBody
    @RequestMapping("addQuestion")
    public Result addQuestion(@RequestBody AddQuestionVo addQuestionVo)/*(@RequestParam("pid") Integer pid,@RequestParam("id") Integer id,@RequestParam("type") Integer type,@RequestBody Question[] questions)*/
    {
        Integer pid = addQuestionVo.getPid();
        Integer id = addQuestionVo.getId();
        Integer type = addQuestionVo.getType();
        List<Question> questions = addQuestionVo.getQuestions();
        if(questions.size()>0)
        {
            int len = questions.size();
            for(int i=0;i<len;++i)
            {
                if(questions.get(i)!=null) {
                    questions.get(i).setType(type);
                    questionService.save(questions.get(i));
                    chapterQuestionService.save(new ChapterQuestion(null, id, questions.get(i).getId()));
                }

            }
        }
        return new Result().success(null);
    }

    @ResponseBody
    @RequestMapping("addChapter")
    public Result addChapter(@RequestParam("pid") Integer pid,@RequestParam("name") String name)
    {
        chapterService.save(new Chapter(null,pid,name));
        return new Result().success(null);
    }


}
