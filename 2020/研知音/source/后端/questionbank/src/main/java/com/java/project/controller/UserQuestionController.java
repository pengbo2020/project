package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.User;
import com.java.project.entity.UserQuestion;
import com.java.project.service.UserQuestionService;
import com.java.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 用户做过的题目，是有答案的 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/user-question")
public class UserQuestionController {

    @Autowired
    UserQuestionService userQuestionService;

    @RequestMapping("favor")
    public Result favor(@RequestParam("qid") Integer qid, @RequestParam("collect") Integer collect, HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
            if(userQuestion!=null) {
                userQuestion.setCollect(collect);
                userQuestionService.updateById(userQuestion);
            }else{
                UserQuestion question = new UserQuestion(null, uid, qid, null, null, null, collect, 0, 0, new Date().toString(), 0);
                userQuestionService.save(question);
            }
            return new Result().success(null);
        }else{
            return new Result().failed();
    }
    }

    @RequestMapping("submitAnswer")
    public Result submitAnswer(@RequestParam("qid") Integer qid,@RequestParam("userAnswer") String userAnswer,@RequestParam("wrong") Integer wrong,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
            if(userQuestion!=null) {
                userQuestion.setAnswer1(userAnswer);
                userQuestion.setWrong(wrong);
                userQuestionService.updateById(userQuestion);
            }else{
                UserQuestion question = new UserQuestion(null, uid, qid, userAnswer, null, null, 0, wrong, 0, new Date().toString(), 0);
                userQuestionService.save(question);
            }
            return new Result().success(null);
        }else{
            return new Result().failed();
        }
    }

    @RequestMapping("submitAnswer2")
    public Result submitAnswer2(@RequestParam("eid") Integer eid,@RequestParam("qid") Integer qid,@RequestParam("userAnswer") String userAnswer,@RequestParam("wrong") Integer wrong,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            UserQuestion userQuestion = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
            if(userQuestion!=null) {
                userQuestion.setAnswer1(userAnswer);
                userQuestion.setWrong(wrong);
                userQuestionService.updateById(userQuestion);
            }else{
                UserQuestion question = new UserQuestion(null, uid, qid, userAnswer, null, null, 0, wrong, 0, new Date().toString(), eid);
                userQuestionService.save(question);
            }
            return new Result().success(null);
        }else{
            return new Result().failed();
        }
    }


    @RequestMapping("resetting")
    public Result resetting(@RequestParam("eid") Integer eid,HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            //userQuestionService.remove(new QueryWrapper<UserQuestion>().eq("exam_id",eid).eq("user_id",uid));
            userQuestionService.removeByExamId(uid,eid);
            return new Result().success(null);
        }else{
            return new Result().failed();
        }
    }

}
