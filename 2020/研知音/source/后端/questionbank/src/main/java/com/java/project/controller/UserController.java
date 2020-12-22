package com.java.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.User;
import com.java.project.service.UserService;
import com.java.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-24
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("set")
    public Result set(HttpServletRequest request)
    {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", 23));
        request.getSession().setAttribute("user",user);
        String id = request.getSession().getId();
        return new Result().success(id);
    }

    @RequestMapping("get")
    public Result get(HttpServletRequest request)
    {
        /*User user = userService.getOne(new QueryWrapper<User>().eq("id", 1));
        request.getSession().setAttribute("user",user);*/
        return new Result().success(request.getSession().getId());
    }

}
