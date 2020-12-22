package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.Feedback;
import com.java.project.entity.User;
import com.java.project.service.FeedbackService;
import com.java.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-10-01
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("/add")
    public Result add(@RequestParam("bid") Integer bid, @RequestParam("type")Integer type, @RequestParam("content")String content, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            Feedback feedback = feedbackService.getOne(new QueryWrapper<Feedback>().eq("user_id", uid).eq("bug_id", bid).eq("type", type));
            if(feedback==null) {
                feedbackService.save(new Feedback(null, uid, bid, type, content, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), 0, 0));
            }else{
                feedback.setContent(content);
                feedbackService.updateById(feedback);
            }
            return new Result().success(null);
        }else{
            return new Result().failed();
        }

    }

}
