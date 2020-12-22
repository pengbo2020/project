package com.java.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.java.project.entity.*;
import com.java.project.service.*;
import com.java.project.utils.RandomRangeUtil;
import com.java.project.utils.Result;
import com.java.project.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 试卷
 * </p>
 *
 * @author zcj
 * @since 2020-05-24
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ExamService examService;

    @Autowired
    UserExamService userExamService;

    @Autowired
    UserQuestionService userQuestionService;

    @Autowired
    KnowledgeBasicService knowledgeBasicService;

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    ExamQuestionService examQuestionService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ImageService imageService;

    @Autowired
    ChapterService chapterService;



    @RequestMapping("getChapters")
    public Result getChapters(@RequestParam("eid") Integer eid,@RequestParam("pid") Integer pid, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            List<QuestionNum> chapters = examService.getChaptersByPid(eid,pid);
            return new Result().success(chapters);
        }
        return new Result().failed();
    }
    @RequestMapping("/getExam")
    public Result getExam(@RequestParam("eid") Integer eid, HttpServletRequest request) {
        return new Result().success(examService.getOne(new QueryWrapper<Exam>().eq("id",eid)));
    }



    @RequestMapping("/get")
    public Result get(@RequestParam("type") Integer type, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            Integer uid = user.getId();
            Exam exam = examService.getExam(uid, type);
            if (exam == null) {
                exam = examService.getRandomOne(type);
            }
            return new Result().success(exam);
        } else {

            return new Result().failed();
        }
    }

    @RequestMapping("/download")
    public Result download(@RequestParam("eid") Integer eid) {
        Exam exam = examService.getOne(new QueryWrapper<Exam>().eq("id", eid));
        return new Result().success(exam);
    }


    @RequestMapping("/start")
    public Result createExam(@RequestParam("eid") Integer eid, HttpServletRequest request) {
        //需要把所有的题目按照指定的格式都给前端传过去
        //redisTemplate.opsForValue().get("user")
        /***
         * {"title":"水是液体？","type":1,"optionList":[{"id":"A","content":"对"},{"id":"B","content":"错"}],"answer":"A","userAnswer":"","userFavor":false,"explain":"难到是固体不成？"},
         * {"title":"电流分有？","type":2,"optionList":[{"id":"A","content":"直流"},{"id":"B","content":"交流"},{"id":"C","content":"直流和交流"}],"answer":"C","userAnswer":"","userFavor":false,"explain":"科技学依据"},
         *  {"title":"酸菜鱼的味道？","type":3,"optionList":[{"id":"A","content":"咸味"},{"id":"B","content":"辣味"},{"id":"C","content":"甜味"},{"id":"D","content":"酸味"}],"answer":"A,B,D","userAnswer":"","userFavor":false,"explain":"你怎么想都行，要的就是这个味，答案只能选A,B,D"},
         *  {"title":"床前（____）光，疑是地上霜。","type":4,"optionList":[{"id":"","content":""}],"answer":"明月","userAnswer":"","userFavor":false,"explain":"问答题没有选项，无法做答，且不参与计分"},
         *   {"title":"什么美国要限制华为？","type":5,"optionList":[{"id":"","content":""}],"answer":"","userAnswer":"","userFavor":false,"explain":"问答题没有选项，无法做答，且不参与计分"},
         * **/
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            List<UserExam> exams = userExamService.list(new QueryWrapper<UserExam>().eq("user_id", uid).eq("exam_id", eid));

            if ((exams!=null && exams.size()==0) || exams == null ) {
                userExamService.save(new UserExam(null, uid, eid, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), null));
            }else{
                UserExam userExam = exams.get(0);
                userExam.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                userExamService.updateById(userExam);
            }
            ArrayList<QuestionVo> questionVos = new ArrayList<>();
            List<Question> questionList = questionService.getQuestions(eid);
            if (questionList != null) {
                questionList.forEach((question) -> {
                    Boolean userFavor = false;
                    String userAnswer="";
                    UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", question.getId()));
                    if (userQuestion != null) {
                        userFavor = userQuestion.getCollect() == 1 ? true : false;
                        userAnswer = userQuestion.getAnswer1();
                    }
                    if (question.getType() < 2) {
                        /***
                         *
                         * Integer qid;
                         *     String title;
                         *     Integer type;
                         *     List<Option> optionList;
                         *     String answer;
                         *     String userAnswer;
                         *     Boolean userFavor;
                         *     String explain;
                         *
                         * **/
                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("A", question.getOptionA()));
                        optionList.add(new Option("B", question.getOptionB()));
                        optionList.add(new Option("C", question.getOptionC()));
                        optionList.add(new Option("D", question.getOptionD()));
                        questionVos.add(new QuestionVo(question.getId(), question.getTitle(), question.getType() + 2, optionList, question.getAnswer1(), userAnswer, userFavor, question.getParse()));
                    } else {
                        List<Option> optionList = new ArrayList<>(); //List<Op>();
                        optionList.add(new Option("", question.getOptionA()));
                        questionVos.add(new QuestionVo(question.getId(), question.getTitle(), 5, optionList, question.getAnswer1(), "", userFavor, question.getParse()));
                    }
                });
            }
            return new Result().success(questionVos);
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("stop")
    public Result stop(@RequestParam("eid") Integer eid, @RequestBody List<QuestionVo> questionVos, Integer remainingTime, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            if (questionVos != null) {
                questionVos.forEach((questionVo) -> {
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
                        UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new Date().toString(), eid);
                        userQuestionService.save(question);
                    } else {
                        userQuestion.setAnswer1(questionVo.getUserAnswer());
                        userQuestion.setCollect(userFavor);
                        userQuestion.setWrong(wrong);
                        userQuestionService.updateById(userQuestion);
                    }
                });

            }
            return new Result().success(null);
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("stopExam")
    public Result stop2(@RequestBody QuestionVo[] questionVos, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            if (questionVos != null && questionVos.length>0) {
                //最后一个qid为nid
                int l =questionVos.length;
                QuestionVo temp = questionVos[l-1];
                Integer eid = temp.getQid();
                for(int i=0;i<l-1;++i)
                {
                    QuestionVo questionVo =questionVos[i];
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
                        UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new Date().toString(), eid);
                        userQuestionService.save(question);
                    } else {
                        userQuestion.setAnswer1(questionVo.getUserAnswer());
                        userQuestion.setCollect(userFavor);
                        userQuestion.setWrong(wrong);
                        userQuestionService.updateById(userQuestion);
                    }
                }

            }
            return new Result().success(null);
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("stop2")
    public Result stop3(@RequestParam("datas") QuestionVo[] questionVos, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            if (questionVos != null && questionVos.length>0) {
                //最后一个qid为nid
                int l =questionVos.length;
                QuestionVo temp = questionVos[l-1];
                Integer eid = temp.getQid();
                for(int i=0;i<l-1;++i)
                {
                    QuestionVo questionVo =questionVos[i];
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
                        UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new Date().toString(), eid);
                        userQuestionService.save(question);
                    } else {
                        userQuestion.setAnswer1(questionVo.getUserAnswer());
                        userQuestion.setCollect(userFavor);
                        userQuestion.setWrong(wrong);
                        userQuestionService.updateById(userQuestion);
                    }
                }

            }
            return new Result().success(null);
        } else {
            return new Result().failed();
        }
    }



    @RequestMapping("stopExam3")
    public Result stop4(@RequestParam("datas") String data,  HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
           // System.out.println(data);
            // String data = object.toJSONString();
            //JSONObject jsonObject = JSON.parseObject(data);
            //String questionVos1 = jsonObject.getString("questionVos");

            //System.out.println(object.toString());

            JSONArray questionVos = JSONArray.parseArray(data);
            int l =questionVos.size();
            QuestionVo questionVo=  JSONObject.parseObject(JSONObject.toJSONString(questionVos.get(l-1)), QuestionVo.class);
            Integer eid = questionVo.getQid();
            //System.out.println(eid);
            for (int i = 0; i < l-1; i++) {

               // System.out.println(questionVos.get(i));
                questionVo=  JSONObject.parseObject(JSONObject.toJSONString(questionVos.get(i)), QuestionVo.class);
                //QuestionVo questionVo = JSONObject.toJavaObject(JSONObject.toJSONString(questionVos.get(i)), QuestionVo.class);

                int wrong = 0;
                int userFavor = questionVo.getUserFavor() == true ? 1 : 0;
                if (questionVo.getType() == 2 || questionVo.getType() == 3) {
                    if (questionVo.getAnswer() != questionVo.getUserAnswer()) {
                        wrong = 1;
                    }
                }
                Integer qid = questionVo.getQid();
                UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
               // System.out.println(userQuestion);
                if (userQuestion == null) {
                    UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new Date().toString(), eid);
                    userQuestionService.save(question);
                } else {
                    userQuestion.setAnswer1(questionVo.getUserAnswer());
                    userQuestion.setCollect(userFavor);
                    userQuestion.setWrong(wrong);
                    userQuestionService.updateById(userQuestion);
                }


                /**
                 *  Integer qid;
                 *     String title;
                 *     Integer type;
                 *     List<Option> optionList;
                 *     String answer;
                 *     String userAnswer;
                 *     Boolean userFavor;
                 *     String explain;
                 *
                 *
                 * */
            }

        /*    if (questionVos != null && questionVos.length>0) {
                //最后一个qid为nid
                int l =questionVos.length;
                QuestionVo temp = questionVos[l-1];
                Integer eid = temp.getQid();
                for(int i=0;i<l-1;++i)
                {
                    QuestionVo questionVo =questionVos[i];
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
                        UserQuestion question = new UserQuestion(null, uid, qid, questionVo.getUserAnswer(), null, null, userFavor, wrong, 0, new Date().toString(), eid);
                        userQuestionService.save(question);
                    } else {
                        userQuestion.setAnswer1(questionVo.getUserAnswer());
                        userQuestion.setCollect(userFavor);
                        userQuestion.setWrong(wrong);
                        userQuestionService.updateById(userQuestion);
                    }
                }

            }*/
            return new Result().success(null);
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("answer")
    public Result answer(@RequestParam("eid") Integer eid, HttpServletRequest request) {
        //答案 知识点 自己的答案 笔记(content 和 path)
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            ArrayList<AnswerVo> questionVos = new ArrayList<>();
            List<Question> questionList = questionService.getQuestions(eid);

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
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("history")
    public Result history(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            List<Exam> exams = examService.getUserExams(uid);
            return new Result().success(exams);
        } else {
            return new Result().failed();
        }
    }

    @RequestMapping("zt")
    public Result zt(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            List<Exam> list = examService.list(new QueryWrapper<Exam>().eq("type", 0));
            for (int i = 0; i < list.size(); ++i) {
                Exam exam = list.get(i);
                //List<UserQuestion> userQuestions = userQuestionService.list(new QueryWrapper<UserQuestion>().eq("exam_id", exam.getId()).eq("user_id",uid));
                UserExam userExam = userExamService.getOne(new QueryWrapper<UserExam>().eq("user_id", uid).eq("exam_id", exam.getId()));

                if (userExam!=null) {
                    list.get(i).setCreateAt("t");
                } else {
                    list.get(i).setCreateAt("f");
                }
            }
            return new Result().success(list);
        }
        return new Result().failed();
    }


    /**
     * type 0 单选 1 多选 2 分析题
     */
    @RequestMapping("getExamQuestions")
    public Result getExamQuestions(@RequestParam("eid") Integer eid, @RequestParam("type") Integer type) {
        List<Question> questions = examService.getQuestionsByExamIdAndType(eid, type);
        return new Result().success(questions);
    }

    //停止答题的
    @RequestMapping("stopExam2")
    public Result stopExam(@RequestParam("eid") Integer eid, @RequestBody @RequestParam("userQuestionList") List<UserQuestion> userQuestionList, Integer remainingTime) {
        UserExam userExam = userExamService.getById(eid);
        userExam.setRemainingTime(remainingTime);
        userExamService.updateById(userExam);
        for (UserQuestion question : userQuestionList) {
            userQuestionService.save(question);   //
        }
        return new Result().success(null);
    }

    @RequestMapping("checkAnswers")        // 答案以及所在的知识点
    public Result checkAnswers(@RequestParam("eid") Integer eid, @RequestParam("type") Integer type) {
        Integer uid = null;
        List<Question> questions = examService.getQuestionsByExamIdAndType(eid, type);
        ArrayList<UserQuestionVo> userQuestionVos = new ArrayList<>();
        for (Question question : questions) {

            Integer qid = question.getId();

            UserQuestionVo userQuestionVo = new UserQuestionVo();
            userQuestionVo.setQuestion(question);
            UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("question_id", qid).eq("exam_id", eid));
            userQuestionVo.setUserQuestion(userQuestion);
            List<KnowledgeBasic> knowledgeBasicList = knowledgeBasicService.getQuestionKnowledge(qid);
            userQuestionVo.setKnowledgeBasicList(knowledgeBasicList);
            UserNote userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", qid));
            userQuestionVo.setUserNote(userNote);
            userQuestionVos.add(userQuestionVo);
        }
        return new Result().success(userQuestionVos);
    }


    @RequestMapping("/hasDone")
    public Result hasDone() {
        return null;
    }

}
