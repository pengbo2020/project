package com.java.project.vo;

public interface UserConstantInterface {
    // 请求的网址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // 你的appid
    //public static final String WX_LOGIN_APPID = "wxd50b946bc1ca9435";
    public static final String WX_LOGIN_APPID = "wxb1a9a6d95453f968";
    // 你的密匙
    //public static final String WX_LOGIN_SECRET = "824a54a9ab87c450b76495996dbdfa45";
    public static final String WX_LOGIN_SECRET = "1d3ecd97b02699617bdd1eeee286a5cd";
    // 固定参数
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";

}
