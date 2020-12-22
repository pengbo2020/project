package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.News;
import com.java.project.service.NewsService;
import com.java.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 时事新闻 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @RequestMapping("getList")
    public Result getList(@RequestParam(value = "pageNumber",defaultValue = "0")Integer pn,@RequestParam(value = "pageSize",defaultValue = "20") Integer ps)
    {
        List<News> news = newsService.getList(pn,ps);
        return new Result().success(news);
    }


    @RequestMapping("detail")
    public Result detail(@RequestParam("id") Integer id)
    {
        return new Result().success(newsService.getOne(new QueryWrapper<News>().eq("id",id)));
    }

}
