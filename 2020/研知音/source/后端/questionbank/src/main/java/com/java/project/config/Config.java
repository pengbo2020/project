package com.java.project.config;

import com.java.project.handlerinterceptor.LoginHandlerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.java.project.mapper")
public class Config implements WebMvcConfigurer {

    @Autowired
    LoginHandlerInterceptor loginHandlerInterceptor;
    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600 * 24);
    }



    /* @Override
     public void addInterceptors(InterceptorRegistry registry) {
         System.out.println("登陆拦截器生效");
         registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/warehouse/user/login", "/warehouse/user/doLogin","warehouse//login.html");
         WebMvcConfigurer.super.addInterceptors(registry);
     }*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**");
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* System.out.println("配置文件已经生效");*/
        String path = System.getProperty("user.dir").replace('\\','/') + "/noteImgs/";
        //static/upload/**是对应resource下工程目录
        registry.addResourceHandler("/noteImgs/**").addResourceLocations("file:"+path);
        path = System.getProperty("user.dir").replace('\\','/') + "/paper/";
        registry.addResourceHandler("/paper/**").addResourceLocations("file:"+path);
        path = System.getProperty("user.dir").replace('\\','/') + "/knowledgeaudio/";
        registry.addResourceHandler("/knowledgeaudio/**").addResourceLocations("file:"+path);
        path = System.getProperty("user.dir").replace('\\','/') + "/images/";
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+path);
        path = System.getProperty("user.dir").replace('\\','/') + "/newsaudio/";
        registry.addResourceHandler("/newsaudio/**").addResourceLocations("file:"+path);

    }

    /**
     * 配置上传文件大小的配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
       // factory.setMaxFileSize(Data);
        //factory.setMaxFileSize("102400KB");
        /// 总上传数据大小
        //factory.setMaxRequestSize("102400KB");
        factory.setMaxFileSize(DataSize.ofMegabytes(5)); //MB
        //factory.setMaxFileSize(DataSize.ofKilobytes(80)); //KB
        //factory.setMaxFileSize(DataSize.ofGigabytes(80)); //Gb
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(50));
        return factory.createMultipartConfig();
    }

   /* @Bean
    public ConfigurableServletWebServerFactory mytomcat()
    {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.set
        return tomcatServletWebServerFactory;
    }*/



    /**
     * 配置上传文件大小的配置
     *
     * @return
     */
 /*   @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(102400);
        /// 总上传数据大小
        factory.setMaxRequestSize(102400);
        return factory.createMultipartConfig();
    }*/

}
