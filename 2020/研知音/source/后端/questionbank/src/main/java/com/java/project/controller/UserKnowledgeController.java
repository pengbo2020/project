package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.User;
import com.java.project.entity.UserKnowledge;
import com.java.project.service.ChapterKnowledgeService;
import com.java.project.service.UserKnowledgeService;
import com.java.project.utils.Result;
import com.java.project.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/user-knowledge")
public class UserKnowledgeController {

    @Autowired
    ChapterKnowledgeService chapterKnowledgeService;

    @Autowired
    UserKnowledgeService userKnowledgeService;


    @RequestMapping("add")
    public Result add(@RequestParam("kid")Integer kid,@RequestParam(value = "playTime",defaultValue = "0") Integer playTime, HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            UserKnowledge userKnowledge = userKnowledgeService.getOne(new QueryWrapper<UserKnowledge>().eq("user_id",uid).eq("knowledge_id",kid));
            if(userKnowledge==null)
            {
                userKnowledgeService.save(new UserKnowledge(null,uid,kid,0,playTime));
            }else{
                userKnowledge.setPlayTime(playTime);
                userKnowledgeService.updateById(userKnowledge);
            }
            return new Result().success(null);
        }

        return new Result().failed();
    }

    @RequestMapping("userFavor")
    public Result setUserFavor(@RequestParam("kid")Integer kid,@RequestParam("favor") Integer favor, HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            UserKnowledge userKnowledge = userKnowledgeService.getOne(new QueryWrapper<UserKnowledge>().eq("user_id",uid).eq("knowledge_id",kid));
            if(userKnowledge==null)
            {
                userKnowledgeService.save(new UserKnowledge(null,uid,kid,favor,null));
            }else{
                userKnowledge.setLearned(favor);
                userKnowledgeService.updateById(userKnowledge);
            }
            return new Result().success(null);
        }

        return new Result().failed();
    }

    @RequestMapping("getNum")
    public Result getNum(HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            Integer uid = user.getId();
            ArrayList<DataVo> list = new ArrayList<>();
            for (int pid = 1; pid < 6; ++pid) {
                Integer cknum = chapterKnowledgeService.getKNum(pid);
                Integer uknum = userKnowledgeService.getUNum(uid,pid);
                list.add(new DataVo(uknum,cknum));
            }
            return new Result().success(list);
        }
        return  new Result().failed();
    }


}
