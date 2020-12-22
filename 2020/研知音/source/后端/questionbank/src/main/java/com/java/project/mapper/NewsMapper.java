package com.java.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.project.entity.News;

import java.util.List;

/**
 * <p>
 * 时事新闻 Mapper 接口
 * </p>
 *
 * @author zcj
 * @since 2020-09-27
 */
public interface NewsMapper extends BaseMapper<News> {

    List<News> selectNewsList(Integer pn, Integer ps);
}
