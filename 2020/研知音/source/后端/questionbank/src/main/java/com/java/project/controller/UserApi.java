package com.java.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.User;
import com.java.project.service.UserService;

import com.java.project.utils.Result;
import com.java.project.utils.HttpClientUtil;
import com.java.project.vo.UserConstantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowCredentials = "true")
public class UserApi {

    @Autowired
    private UserService userService;


    @PostMapping("/me/login")
    public Result user_login(
            @RequestParam("code") String code,
            @RequestParam("userHead") String userHead,
            @RequestParam("userName") String userName,
            @RequestParam("userGender") String userGender,
            @RequestParam("userCity") String userCity,
            @RequestParam("userProvince") String userProvince,
            HttpServletRequest request){
        User user1 = (User) request.getSession().getAttribute("user");
        // 配置请求参数
        if(user1==null) {
            Map<String, String> param = new HashMap<>();
            param.put("appid", UserConstantInterface.WX_LOGIN_APPID);
            param.put("secret", UserConstantInterface.WX_LOGIN_SECRET);
            param.put("js_code", code);
            param.put("grant_type", UserConstantInterface.WX_LOGIN_GRANT_TYPE);
            // 发送请求
            String wxResult = HttpClientUtil.doGet(UserConstantInterface.WX_LOGIN_URL, param);
            JSONObject jsonObject = JSONObject.parseObject(wxResult);
            // 获取参数返回的
            String session_key = jsonObject.get("session_key").toString();
            String open_id = jsonObject.get("openid").toString();
            // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
            User user = userService.getOne(new QueryWrapper<User>().eq("userOpenid", open_id));
            User insert_user = new User();
            if (user != null) {
                user.setUserNewLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                userService.updateById(user);
            } else {

                insert_user.setUserHead(userHead);
                insert_user.setUserName(userName);
                insert_user.setUserGender(userGender);
                insert_user.setUserNewLogin(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                insert_user.setUserCity(userCity);
                insert_user.setUserProvince(userProvince);
                insert_user.setUserOpenid(open_id);
                //System.out.println("insert_user:" + insert_user.toString());
                // 添加到数据库
                Boolean flag = userService.save(insert_user);
                if (!flag) {
                    return new Result().failed();
                }
                user=insert_user;
            }
            // 封装返回小程序
           /* Map<String, String> result = new HashMap<>();
            result.put("session_key", session_key);
            result.put("open_id", open_id);*/
            request.getSession().setAttribute("user",user);
            System.out.println((User) request.getSession().getAttribute("user"));
            //System.out.println(request.getSession().getMaxInactiveInterval());
            return new Result().success(request.getSession().getId());
        }else{
            return new Result().success(request.getSession().getId());
        }


    }

}

