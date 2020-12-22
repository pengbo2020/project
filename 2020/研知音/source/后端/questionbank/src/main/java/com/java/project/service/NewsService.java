package com.java.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.project.entity.News;

import java.util.List;

/**
 * <p>
 * 时事新闻 服务类
 * </p>
 *
 * @author zcj
 * @since 2020-09-27
 */
public interface NewsService extends IService<News> {

    List<News> getList(Integer pn, Integer ps);
}
