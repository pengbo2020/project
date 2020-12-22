package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.*;
import com.java.project.service.*;
import com.java.project.utils.Result;
import com.java.project.vo.AnswerVo;
import com.java.project.vo.Note;
import com.java.project.vo.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 题目 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    ExamQuestionService examQuestionService;

    @Autowired
    UserQuestionService userQuestionService;

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    ImageService imageService;



    /*
    * type 0 collect
    * type 1 wrong
    * type 2 mark
    * */
    @RequestMapping("get")
    public Result get(@RequestParam("type") Integer type, @RequestParam(value = "pn",defaultValue = "0") Integer pn, @RequestParam(value = "ps",defaultValue = "10") Integer ps, HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<AnswerVo> questionVos = new ArrayList<>();
            Integer collect = 0;
            Integer wrong = 0;
            Integer mark = 0;
            switch (type) {
                case 0:
                    collect = 1;
                    break;
                case 1:
                    wrong = 1;
                    break;
                case 2:
                    mark = 1;
                    break;
            }


            List<Question> questionList = questionService.getQuestionsByType(uid, collect, wrong, mark, pn, ps);

            if (questionList != null) {
                questionList.forEach((question) -> {
                    Boolean userFavor = false;
                    String answer = null;
                    UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", question.getId()));
                    if (userQuestion != null) {
                        userFavor = userQuestion.getCollect() == 1 ? true : false;
                        answer = userQuestion.getAnswer1();
                    }
                    UserNote userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", question.getId()));
                    Note note = new Note();
                    if (userNote != null) {
                        List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("pid", userNote.getId()).eq("type", 2));
                        note.setContent(userNote.getDetail());
                        note.setImageList(imageList);
                    }
                    // System.out.println(question);

                    if (question.getType() < 2) {

                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("A", question.getOptionA()));
                        optionList.add(new Option("B", question.getOptionB()));
                        optionList.add(new Option("C", question.getOptionC()));
                        optionList.add(new Option("D", question.getOptionD()));
                        //System.out.println(new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));

                        questionVos.add(new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));
                    } else {
                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("", question.getOptionA()));
                        questionVos.add(new AnswerVo(question.getId(), question.getTitle(), 5, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));
                    }
                });
            }

            return new Result().success(questionVos);
        }else{
            return new Result().failed();
        }

    }


    @RequestMapping("getByTypeAndChapterId")
    public Result getByTypeAndChapterId(@RequestParam("type") Integer type,@RequestParam("cid") Integer cid,  @RequestParam(value = "pn",defaultValue = "0") Integer pn, @RequestParam(value = "ps",defaultValue = "10") Integer ps, HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<AnswerVo> questionVos = new ArrayList<>();
            Integer collect = 0;
            Integer wrong = 0;
            Integer mark = 0;
            switch (type) {
                case 0:
                    collect = 1;
                    break;
                case 1:
                    wrong = 1;
                    break;
                case 2:
                    mark = 1;
                    break;
            }


            List<Question> questionList = questionService.getQuestionsByTypeAndChapterId(uid,cid, collect, wrong, mark, pn, ps);

            if (questionList != null) {
                questionList.forEach((question) -> {
                    Boolean userFavor = false;
                    String answer = null;
                    UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", question.getId()));
                    if (userQuestion != null) {
                        userFavor = userQuestion.getCollect() == 1 ? true : false;
                        answer = userQuestion.getAnswer1();
                    }
                    UserNote userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", question.getId()));
                    Note note = new Note();
                    if (userNote != null) {
                        List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("pid", userNote.getId()).eq("type", 2));
                        note.setContent(userNote.getDetail());
                        note.setImageList(imageList);
                    }
                    // System.out.println(question);

                    if (question.getType() < 2) {

                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("A", question.getOptionA()));
                        optionList.add(new Option("B", question.getOptionB()));
                        optionList.add(new Option("C", question.getOptionC()));
                        optionList.add(new Option("D", question.getOptionD()));
                        //System.out.println(new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));

                        questionVos.add(new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));
                    } else {
                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("", question.getOptionA()));
                        questionVos.add(new AnswerVo(question.getId(), question.getTitle(), 5, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));
                    }
                });
            }

            return new Result().success(questionVos);
        }else{
            return new Result().failed();
        }

    }

    @RequestMapping("getone")
    public Result getone(@RequestParam("pid") Integer qid,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            Question question = questionService.getById(qid);
            AnswerVo answerVo = null;
            if (question != null) {
                Boolean userFavor = false;
                String answer = null;
                UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", question.getId()));
                if (userQuestion != null) {
                    userFavor = userQuestion.getCollect() == 1 ? true : false;
                    answer = userQuestion.getAnswer1();
                }
                UserNote userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", question.getId()));
                Note note = new Note();
                if (userNote != null) {
                    List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("pid", userNote.getId()).eq("type", 2));
                    note.setContent(userNote.getDetail());
                    note.setImageList(imageList);
                }
                // System.out.println(question);

                if (question.getType() < 2) {

                    List<Option> optionList = new ArrayList<>(); //List<Op>();
                    optionList.add(new Option("A", question.getOptionA()));
                    optionList.add(new Option("B", question.getOptionB()));
                    optionList.add(new Option("C", question.getOptionC()));
                    optionList.add(new Option("D", question.getOptionD()));
                    //System.out.println(new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note));

                    answerVo = new AnswerVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note);
                } else {
                    List<Option> optionList = new ArrayList<>(); //List<Op>();
                    optionList.add(new Option("", question.getOptionA()));
                    answerVo = new AnswerVo(question.getId(), question.getTitle(), 5, optionList, question.getAnswer1(), answer, userFavor, question.getParse(), question.getKnowledge(), note);
                }
            }
            return new Result().success(answerVo);
        }else{
            return new Result().failed();
        }
    }

    @RequestMapping("add")
    public Result add()
    {
        int eid=1;
        for(int i=0;i<16;++i)
        {
            Question question = new Question(null,"在辛亥革命爆发前，孙中山领导中国同盟会发动的武装起义中影响最大的是","黄花岗起义","黄花岗起义","黄花岗起义","黄花岗起义",0,"A",null,"测试",3,null,null,2105,0,0,0,0);
            questionService.save(question);
            examQuestionService.save(new ExamQuestion(null,eid,question.getId()));
        }
        for(int i=0;i<17;++i)
        {
            Question question2 = new Question(null,"在辛亥革命爆发前，孙中山领导中国同盟会发动的武装起义中影响最大的是","黄花岗起义","黄花岗起义","黄花岗起义","黄花岗起义",1,"AB",null,"测试",3,null,null,2105,0,0,0,0);
            questionService.save(question2);
            examQuestionService.save(new ExamQuestion(null,eid,question2.getId()));
        }

        for(int i=0;i<4;++i)
        {
            Question question3 = new Question(null,"结合材料回答问题：\n" +
                    "材料1\n" +
                    "戊戌六君子之一的谭嗣同曾有一首狱中绝笔诗，是这样写的：“有心杀贼，无力回天。死得其所，快哉快哉！”写完以后，谭嗣同英勇就义。\n" +
                    "材料2\n" +
                    "近代以后，争取民族独立、人民解放和实现国家富强、人民幸福就成为中国人民的历史任务。在旧式的农民战争走到尽头，不触动封建根基的自强运动和改良主义屡屡碰壁，资产阶级革命派领 导的革命和西方资本主义的其他种种方案纷纷破产的情况下，十月革命一声炮响，为中国送来了马 克思列宁主义，给苦苦探寻救亡图存出路的中国人民指明了前进方向、提供了全新选择。\n" +
                    "——习近平2018年5月4日在纪念马克思诞辰200周年大会上的讲'话","维新派“无力回天”的主要原因是什么？","维新运动的夭折说明了什么？",null,null,1,"A",null,"测试",3,null,null,2105,0,0,0,0);
            questionService.save(question3);
            examQuestionService.save(new ExamQuestion(null,eid,question3.getId()));
        }
        return new Result().success(null);
    }



}
