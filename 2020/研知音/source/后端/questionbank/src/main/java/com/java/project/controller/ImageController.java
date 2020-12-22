package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.Image;
import com.java.project.service.ImageService;
import com.java.project.utils.RandomRangeUtil;
import com.java.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping("getBannerImage")
    public Result getBannerImage()
    {
        Image image = imageService.getOne(new QueryWrapper<Image>().eq("type", 5));
        return new Result().success(image);
    }

    @RequestMapping("getHomePageImage")
    public Result getHomePageImage()
    {
        List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("type", 4));
        return new Result().success(imageList);
    }

    @RequestMapping("getSentence")
    public Result getSentence()
    {
        List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("type", 6));
        if(imageList!=null && imageList.size()>0)
        {
            if(imageList.size()>1) {
                List<Integer> random = RandomRangeUtil.getRandom(imageList.size(), 1);
                return new Result().success(imageList.get(random.get(0)));
            }else{
                return new Result().success(imageList.get(0));
            }
        }
        return new Result().failed();
    }
}
