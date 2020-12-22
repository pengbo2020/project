package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.*;
import com.java.project.service.*;
import com.java.project.utils.Result;
import com.java.project.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-22
 */
@RestController
@RequestMapping("/chapter-question")
public class ChapterQuestionController {

    @Autowired
    ChapterQuestionService chapterQuestionService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    UserQuestionService userQuestionService;

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    ImageService imageService;

    @Autowired
    QuestionService questionService;

    @RequestMapping("getChildChapter")
    public Result getChild(@RequestParam("pid") Integer pid, HttpServletRequest request)
    {
         User user = (User)request.getSession().getAttribute("user");
         if(user!=null) {
             Integer uid = user.getId();
             List<ChapterVo> chapters = chapterService.getChapters(uid, pid);
             return new Result().success(chapters);
         }else{
             return new Result().failed();
         }
    }

    @RequestMapping("getQuestions")
    public Result getQuestions(@RequestParam("pid") Integer pid,@RequestParam(value = "pn",defaultValue = "0") Integer pn,@RequestParam(value = "ps",defaultValue = "20") Integer ps,HttpServletRequest request )
    {
        User user = (User)request.getSession().getAttribute("user");
        ArrayList<AnswerVo> questionVos = new ArrayList<>();
        if(user!=null) {
            Integer uid = user.getId();

            List<Question> questionList = questionService.getQuestionsByChapterId(pid, pn, ps);

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
        }else{
            return new Result().failed();
        }

        return new Result().success(questionVos);
    }

    @RequestMapping("submit")
    public Result submit(@RequestBody QuestionVo questionVo,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            if (questionVo != null) {
                //questionVo.getQid();
                int wrong = 0;
                int userFavor = questionVo.getUserFavor() == true ? 1 : 0;
                if (questionVo.getType() == 2 || questionVo.getType() == 3) {
                    if (questionVo.getAnswer() != questionVo.getUserAnswer()) {
                        wrong = 1;
                    }
                }
                Integer qid = questionVo.getQid();
                UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
                if (userQuestion == null) {
                    UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), null);
                    userQuestionService.save(question);
                } else {
                    userQuestion.setAnswer1(questionVo.getUserAnswer());
                    userQuestion.setCollect(userFavor);
                    userQuestion.setWrong(wrong);
                    userQuestionService.updateById(userQuestion);
                }
            }
            return new Result().success(null);
        }else{
            return new Result().failed();
        }

    }


    @RequestMapping("history")
    public Result history(@RequestParam("pid") Integer pid,@RequestParam(value = "pn",defaultValue = "0") Integer pn,@RequestParam(value = "ps",defaultValue = "20") Integer ps,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<AnswerVo> questionVos = new ArrayList<>();
            List<Question> questionList = questionService.getHasDoneQuestionsByChapterId(uid, pid,pn,ps);

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




}
