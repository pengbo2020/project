package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.News;
import com.java.project.mapper.NewsMapper;
import com.java.project.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 时事新闻 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-09-27
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    NewsMapper newsMapper;
    @Override
    public List<News> getList(Integer pn, Integer ps) {
        return newsMapper.selectNewsList((pn*ps),ps);
    }
}
