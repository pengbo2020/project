package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.Chapter;
import com.java.project.entity.ChapterKnowledge;
import com.java.project.entity.User;
import com.java.project.service.ChapterKnowledgeService;
import com.java.project.service.ChapterService;
import com.java.project.utils.Result;
import com.java.project.vo.ChapterAndNum;
import com.java.project.vo.ChapterKnowledgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 章节和知识点 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/chapter-knowledge")
public class ChapterKnowledgeController {

    @Autowired
    ChapterService chapterService;

    @Autowired
    ChapterKnowledgeService chapterKnowledgeService;

    @RequestMapping("getList")
    public Result getChapterAndChildChapter(@RequestParam("pid") Integer pid, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            ArrayList<ChapterAndNum> chapterKnowledgeVos = new ArrayList<>();
            List<Chapter> chapters = chapterService.list(new QueryWrapper<Chapter>().eq("pid", pid));
            for (Chapter chapter : chapters) {
                ArrayList<Integer> cknumList = new ArrayList<>();
                ArrayList<Integer> uknumList = new ArrayList<>();
                Integer childPid = chapter.getId();
                List<Chapter> childChapters = chapterService.list(new QueryWrapper<Chapter>().eq("pid", childPid));
                for (Chapter childChapter : childChapters) {
                    Integer cid = childChapter.getId();
                    int cCount = chapterKnowledgeService.count(new QueryWrapper<ChapterKnowledge>().eq("chapter_id", cid));
                    cknumList.add(cCount);
                    int uCount = chapterKnowledgeService.getUserCount(uid,cid);
                    uknumList.add(uCount);
                }
                chapterKnowledgeVos.add(new ChapterAndNum(chapter, childChapters,cknumList,uknumList));
            }
            return new Result().success(chapterKnowledgeVos);
        }
        return new Result().failed();
    }

    //getCollectionList

    @RequestMapping("getCollectionList")
    public Result getCollectionList(@RequestParam("pid") Integer pid, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            ArrayList<ChapterAndNum> chapterKnowledgeVos = new ArrayList<>();
            List<Chapter> chapters = chapterKnowledgeService.getCollectionChapter(uid,pid);
            for (Chapter chapter : chapters) {
                ArrayList<Integer> cknumList = new ArrayList<>();
                ArrayList<Integer> uknumList = new ArrayList<>();
                Integer childPid = chapter.getId();
                List<Chapter> childChapters = chapterKnowledgeService.getCollectionChildChapter(uid,childPid);
                for (Chapter childChapter : childChapters) {
                    Integer cid = childChapter.getId();
                    int cCount = chapterKnowledgeService.count(new QueryWrapper<ChapterKnowledge>().eq("chapter_id", cid));
                    cknumList.add(0);
                    int uCount = chapterKnowledgeService.getCollectionUserCount(uid,cid);
                    uknumList.add(uCount);
                }
                chapterKnowledgeVos.add(new ChapterAndNum(chapter, childChapters,cknumList,uknumList));
            }
            return new Result().success(chapterKnowledgeVos);
        }
        return new Result().failed();
    }

}
