package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.KnowledgeBasic;
import com.java.project.entity.User;
import com.java.project.entity.UserKnowledge;
import com.java.project.service.KnowledgeBasicService;
import com.java.project.service.UserKnowledgeService;
import com.java.project.utils.Result;
import com.java.project.vo.KnowledgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 知识点 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/knowledge-basic")
public class KnowledgeBasicController {

    @Autowired
    KnowledgeBasicService knowledgeBasicService;

    @Autowired
    UserKnowledgeService userKnowledgeService;

    @RequestMapping("get")
    public Result get(@RequestParam(value = "pn",defaultValue = "0") Integer pn, @RequestParam(value = "ps",defaultValue = "10") Integer ps, @RequestParam(value = "cid",defaultValue = "-1")Integer cid, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            List<KnowledgeVo> knowledgeVos = null;
            if (cid == -1) {
                knowledgeVos = knowledgeBasicService.getKnowledge(pn, ps);
            } else {
                knowledgeVos = knowledgeBasicService.getKnowledgeByChapterId(cid, pn, ps);
            }


            for (KnowledgeVo knowledgeVo : knowledgeVos) {
                KnowledgeBasic knowledge = knowledgeVo.getKnowledge();
                Integer kid = knowledge.getId();
                UserKnowledge userKnowledge = userKnowledgeService.getOne(new QueryWrapper<UserKnowledge>().eq("user_id", uid).eq("knowledge_id", kid));
                if(userKnowledge==null)
                {
                    //md5  'n'  't'  'f'  -1 未听过 0 听过 未收藏 1 收藏
                    knowledge.setMd5("n");
                }else{
                    if(userKnowledge.getLearned()==1)
                    {
                        knowledge.setMd5("t");
                    }else{
                        knowledge.setMd5("f");
                    }
                }

            }

            return new Result().success(knowledgeVos);
        }
        return new Result().failed();
    }
    @RequestMapping("getCollection")
    public Result getCollection(@RequestParam(value = "pn",defaultValue = "0") Integer pn, @RequestParam(value = "ps",defaultValue = "10") Integer ps, @RequestParam(value = "cid",defaultValue = "-1")Integer cid, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            List<KnowledgeVo> knowledgeVos = null;
            knowledgeVos = knowledgeBasicService.getCollectionKnowledgeByChapterId(uid,cid, pn, ps);


            for (KnowledgeVo knowledgeVo : knowledgeVos) {
                KnowledgeBasic knowledge = knowledgeVo.getKnowledge();
                Integer kid = knowledge.getId();
                UserKnowledge userKnowledge = userKnowledgeService.getOne(new QueryWrapper<UserKnowledge>().eq("user_id", uid).eq("knowledge_id", kid));
                if(userKnowledge==null)
                {
                    //md5  'n'  't'  'f'  -1 未听过 0 听过 未收藏 1 收藏
                    knowledge.setMd5("n");
                }else{
                    if(userKnowledge.getLearned()==1)
                    {
                        knowledge.setMd5("t");
                    }else{
                        knowledge.setMd5("f");
                    }
                }

            }

            return new Result().success(knowledgeVos);
        }
        return new Result().failed();
    }


}
