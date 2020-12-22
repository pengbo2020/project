package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.*;
import com.java.project.service.*;
import com.java.project.utils.Result;
import com.java.project.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 章节 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserQuestionService userQuestionService;

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @RequestMapping("getCidAndQid")
    public Result getCidAndQid(@RequestParam("eid") Integer eid,HttpServletRequest request)
    {

        List<DataVo> dataVos = chapterService.getCidAndQid(eid);
        return new Result().success(dataVos);
    }

    @RequestMapping("get")
    public Result get(@RequestParam("pid") Integer pid,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            List<Chapter> chapters = chapterService.list(new QueryWrapper<Chapter>().eq("pid", pid));
            List<DataVo> chapterQuestionNum = chapterService.getQuestionNumByPid(pid);
            List<DataVo> userQuestionNum = userService.getQuestionNumByUid(uid,pid);
            HashMap<Integer, Integer> map = new HashMap<>();
            HashMap<Integer, Integer> map2 = new HashMap<>();
            ArrayList<QuestionNum> list = new ArrayList<>();

            for (DataVo dataVo : chapterQuestionNum) {
                map.put(dataVo.getPid(),dataVo.getNum());
            }

            for (DataVo dataVo : userQuestionNum) {
                map2.put(dataVo.getPid(),dataVo.getNum());
            }

            for (Chapter chapter : chapters) {
                list.add(new QuestionNum(chapter.getId(),chapter.getPid(),chapter.getName(),map.get(chapter.getId()),map2.get(chapter.getId())));
            }

            return new Result().success(list);
        }
        return new Result().failed();
    }

    @RequestMapping("getScheDule")
    public Result getScheDule(@RequestParam("pid") Integer pid,HttpServletRequest request)
    {

        User user = (User)request.getSession().getAttribute("user");
        List<DataVo> cqnums;
        List<DataVo> uqnums;
        ArrayList<UserProgress> userProgresses = new ArrayList<>();
        if(user!=null) {
            Integer uid = user.getId();
            cqnums= chapterService.getTotalChapterQuestionNum(pid);
            uqnums= userService.getTotalUserQuestionNum(uid,pid);
            HashMap<Integer, HashMap<String, Integer>> map = new HashMap<>();


            for (DataVo dataVo : cqnums) {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                hashMap.put("cqnum",dataVo.getNum());
                map.put(dataVo.getPid(),hashMap);
            }

            for (DataVo dataVo : uqnums) {
                map.get(dataVo.getPid()).put("uqnum",dataVo.getNum());
            }
            return new Result().success(map);
        }
        return new Result().failed();

    }


    @RequestMapping("getChapterByType")
    public Result getChapterByType(@RequestParam("pid") Integer pid,@RequestParam("type") Integer type,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<ChapterVo> chapterVos = new ArrayList<>();
            List<Chapter> chapters=null;
            if(type==0)
            {
                chapters = chapterService.getWrongQuestionChapter(uid,pid);
            }else if(type==1)
            {
                chapters = chapterService.getCollectQuestionChapter(uid,pid);
            }else{
                chapters = chapterService.getMarkQuestionChapter(uid,pid);
            }
            Integer wrong=0;
            Integer collect=0;
            Integer mark=0;
            switch(type)
            {
                case 0:wrong=1;break;
                case 1:collect=1;break;
                case 2:mark=1;break;
            }
            for (Chapter chapter : chapters) {
                Integer cid = chapter.getId();
                Integer num = chapterService.getChapterQuestionNum(uid,cid,collect,wrong,mark);
                chapterVos.add(new ChapterVo(chapter.getId(),chapter.getPid(),chapter.getName(),num));
            }
            return new Result().success(chapterVos);



        }
        return new Result().failed();

    }




    @RequestMapping("getChapterByType2")
    public Result getChapterByType2(@RequestParam("pid") Integer pid,@RequestParam("type") Integer type,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            Integer wrong=0;
            Integer collect=0;
            Integer mark=0;
            switch(type)
            {
                case 0:collect=1;break;
                case 1:wrong=1;break;
                case 2:mark=1;break;
            }

            List<QuestionNum> chapters = chapterService.getUserChapterByType(uid,pid,collect,wrong,mark);

            return new Result().success(chapters);


        }
        return new Result().failed();

    }

    @RequestMapping("getQuestions")
    public Result getChapterQuestion(@RequestParam("cid") Integer cid,@RequestParam(value = "pn",defaultValue = "0") Integer pn,@RequestParam(value = "ps",defaultValue = "20") Integer ps,HttpServletRequest request)
    {

        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<AnswerVo> questionVos = new ArrayList<>();



            List<Question> questionList = questionService.getQuestionsByChapterId(cid,pn,ps);

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
