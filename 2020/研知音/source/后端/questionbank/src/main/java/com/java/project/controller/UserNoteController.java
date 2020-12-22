package com.java.project.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.*;
import com.java.project.service.ImageService;
import com.java.project.service.QuestionService;
import com.java.project.service.UserNoteService;
import com.java.project.service.UserQuestionService;
import com.java.project.utils.Result;
import com.java.project.vo.Note;
import org.apache.http.impl.BHttpConnectionBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.Base64.Encoder;

/**
 * <p>
 * 用户笔记 前端控制器
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@CrossOrigin
@RestController
@RequestMapping("/user-note")
public class UserNoteController {

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserQuestionService  userQuestionService;

    @RequestMapping("modify")
    public Result modify(@RequestParam("qid") Integer qid,HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();
            UserNote userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", qid));
            Note note = new Note();
            if (userNote != null) {
                List<Image> imageList = imageService.list(new QueryWrapper<Image>().eq("pid", userNote.getId()).eq("type", 2));
                note.setContent(userNote.getDetail());
                note.setImageList(imageList);
            }
            return new Result().success(note);
        }else{
            return new Result().failed();
        }
    }

    @RequestMapping("addContent")
    public Result add(@RequestParam("qid") Integer qid, @RequestParam("content") String content, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Integer uid = user.getId();

            UserQuestion question = userQuestionService.getOne(new QueryWrapper<UserQuestion>().eq("user_id", uid).eq("question_id", qid));
            if (question != null) {
                question.setMark(1);
                userQuestionService.updateById(question);
            }

            UserNote userNote = null;
            userNote = userNoteService.getOne(new QueryWrapper<UserNote>().eq("user_id", uid).eq("question_id", qid));
            if (userNote != null) {
                userNote.setDetail(content);
                userNoteService.updateById(userNote);
            } else {
                userNote = new UserNote(null, uid, content, 1, new Date().toString(), null, 0, qid);
                userNoteService.save(userNote);
            }
            return new Result().success(userNote);
        }else{
            return new Result().failed();
        }
    }
    @RequestMapping("addImg3")
    public Result Base64ToImage(@RequestParam("nid") Integer nid, @RequestParam("img") String imgStr,@RequestParam("type") String fileType) { // 对字节数组字符串进行Base64解码并生成图片

        System.out.println(nid);
        System.out.println(imgStr);
        System.out.println(fileType);

        if (imgStr==null || imgStr.equals("")) // 图像数据为空
            return new Result().failed();
        imgStr =  JSON.toJSONString(imgStr);
        System.out.println(imgStr);
        Base64.Decoder decoder = Base64.getDecoder();
        String filepath = System.getProperty("user.dir") + "/noteImgs";
        String newFileName = UUID.randomUUID().toString().substring(0, 10);
        String imgFilePath = filepath + newFileName + "."+fileType;

        try {
            // Base64解码
            byte[] b = decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            return new Result().success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

           // return new Result().failed();
        }
    }



    @RequestMapping("addImg")
    public  Result addImg(@RequestParam(value = "nid",required = false)Integer nid, HttpServletRequest request)
    {
       // MultipartHttpServletRequest params =  (MultipartHttpServletRequest) request;
        MultipartFile mf = ((MultipartHttpServletRequest) request).getFile("img");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        request= resolver.resolveMultipart(request);
        System.out.println(request.getParameter("nid"));
      /*  String queryString = request.getQueryString();
        System.out.println(queryString);*/

        //Integer nid = Integer.parseInt(params.getParameter("nid"));
        /**
         * connection
         * origin
         * user-agent
         * accept
         * accept-encoding
         * cookie
         * *****/

        /*Enumeration<String> names = request.getHeaderNames();
        while (names.nextElement()!=null)
        {
            System.out.println(names.nextElement());

        }*/

        System.out.println(mf);
        System.out.println(nid);


        /*Map<String, String[]> map = request.getParameterMap();
        map.forEach((k,v)->{
            System.out.println(k);
            for (String s : v) {
                System.out.println(s);
            }
        });
        Integer nid =  Integer.parseInt(request.getParameter("nid"));
        System.out.println(request.getParameter("nid"));*/


        // CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //List<Image> list = imageService.list(new QueryWrapper<Image>().eq("pid", nid).eq("type", 2));
        int count=0;
        String res="";
        //获取前台提交图片
        /* if(delete==0) {*/
       /* if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;*/
            String filepath = System.getProperty("user.dir") + "/noteImgs";
          /*  Map<String, MultipartFile> fileMap = multiRequest.getFileMap();*/
            Image image1=null;
            String fileName = null;
/*
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                count++;*/
                // 获取单个文件
                //MultipartFile mf = entity.getValue(); // 获得原始文件名
                // System.out.println(mf.getName());
                fileName = mf.getOriginalFilename(); // 截取文件类型; 这里可以根据文件类型进行判断


                //System.out.println(fileName);
                if (fileName.lastIndexOf('.') != -1) {
                    long size = mf.getSize();
                    if(size<1048576*3){
                        String fileType = fileName.substring(fileName.lastIndexOf('.'));
                        try {
                            // 截取上传的文件名称
                            String newFileName = UUID.randomUUID().toString().substring(0, 10);

                            //图片转换成io，存到数据库
                            // byte[] image = mf.getBytes();
                            // 拼接上传文件位置
                            // Image image1 = new Image(null,nid,newFileName,new Date(),2);


                            //保存到服务器
                            String newfilePath = filepath + File.separatorChar + newFileName + fileType;
                            //System.out.println("拼接好的文件路径地址------------->>>>>>>>" + newfilePath);
                            // 重新组装文件路径，用于保存在list集合中
                            File dest = new File(filepath);
                            // 判断文件夹不存在就创建
                            if (!dest.exists()) {
                                dest.mkdirs();
                            }
                            // 创建文件实例
                            File uploadFile = new File(newfilePath);
                            // 判断文件已经存在，则删除该文件
                            if (uploadFile.exists()) {
                                uploadFile.delete();
                            }
                            mf.transferTo(uploadFile);
                            /*if (count <= list.size()) {
                                list.get(count - 1).setPath(newFileName + fileType);
                                imageService.updateById(list.get(count - 1));
                            } else {
                                 image1 = new Image(null, nid, newFileName + fileType, null, 2);
                                imageService.save(image1);
                                return new Result().success(image1);
                            }*/
                            image1 = new Image(null, nid, newFileName + fileType, null, 2);
                            imageService.save(image1);
                            return new Result().success(image1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        return new Result().success("上传文件过大");
                    }

                }else{
                    //System.out.println(fileName);
                    return new Result().success("在这个地方出错了");
                }
            //}


                /*for (int i = count + 1; i <= list.size(); ++i) {
                    imageService.removeById(list.get(i - 1).getId());
                }*/
       /* }
        return new Result().success("没有文件");*/
            /*else {
                if (list != null) {
                    System.out.println("hello world");
                    for (int i = 0; i < list.size(); ++i) {
                        imageService.removeById(list.get(i).getId());
                    }
                }
            }*/
       /* }else{
            if(list !=null)
            {
                //System.out.println("hello world");
                for(int i=0;i<list.size();++i)
                {
                    imageService.removeById(list.get(i).getId());
                }
            }
        }*/
        /*if(res.length()==0) {
            return new Result().success(null);
        }else{

            res="第"+(res.charAt(res.length()-1)==',' ? res.substring(0,res.length()-1):res)+"张图片大小超出限制";
            return new Result().success(res);

        }*/
        return new Result().success("怎么回事");
    }

    @RequestMapping("addImg1")
    public  Result addImg1(@RequestParam("nid") Integer nid, HttpServletRequest request)
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //List<Image> list = imageService.list(new QueryWrapper<Image>().eq("pid", nid).eq("type", 2));
        int count=0;
        String res="";
        //获取前台提交图片
       /* if(delete==0) {*/
            /*if (multipartResolver.isMultipart(request)) {*/
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\noteImgs";
                String filepath = System.getProperty("user.dir").replace('\\','/') + "/noteImgs/";
                Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
                Image image1=null;
                String fileName = null;

                for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                    count++;
                    // 获取单个文件
                    MultipartFile mf = entity.getValue(); // 获得原始文件名
                   // System.out.println(mf.getName());
                    fileName = mf.getOriginalFilename(); // 截取文件类型; 这里可以根据文件类型进行判断


                    //System.out.println(fileName);
                    if (fileName.lastIndexOf('.') != -1) {
                        long size = mf.getSize();
                         if(size<1048576*3){
                        String fileType = fileName.substring(fileName.lastIndexOf('.'));
                        try {
                            // 截取上传的文件名称
                            String newFileName = UUID.randomUUID().toString().substring(0, 10);

                            //图片转换成io，存到数据库
                            // byte[] image = mf.getBytes();
                            // 拼接上传文件位置
                            // Image image1 = new Image(null,nid,newFileName,new Date(),2);


                            //保存到服务器
                            String newfilePath = filepath + File.separatorChar + newFileName + fileType;
                            //System.out.println("拼接好的文件路径地址------------->>>>>>>>" + newfilePath);
                            // 重新组装文件路径，用于保存在list集合中
                            File dest = new File(filepath);
                            // 判断文件夹不存在就创建
                            if (!dest.exists()) {
                                dest.mkdirs();
                            }
                            // 创建文件实例
                            File uploadFile = new File(newfilePath);
                            // 判断文件已经存在，则删除该文件
                            if (uploadFile.exists()) {
                                uploadFile.delete();
                            }
                            mf.transferTo(uploadFile);
                            /*if (count <= list.size()) {
                                list.get(count - 1).setPath(newFileName + fileType);
                                imageService.updateById(list.get(count - 1));
                            } else {
                                 image1 = new Image(null, nid, newFileName + fileType, null, 2);
                                imageService.save(image1);
                                return new Result().success(image1);
                            }*/
                            image1 = new Image(null, nid, newFileName + fileType, null, 2);
                            imageService.save(image1);
                            return new Result().success(image1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                             return new Result().success("上传文件过大");
                         }

                    }else{
                        System.out.println(fileName);
                        return new Result().success("在这个地方出错了");
                    }
                }


                /*for (int i = count + 1; i <= list.size(); ++i) {
                    imageService.removeById(list.get(i - 1).getId());
                }*/
           // }
        return new Result().success("没有文件");
            /*else {
                if (list != null) {
                    System.out.println("hello world");
                    for (int i = 0; i < list.size(); ++i) {
                        imageService.removeById(list.get(i).getId());
                    }
                }
            }*/
       /* }else{
            if(list !=null)
            {
                //System.out.println("hello world");
                for(int i=0;i<list.size();++i)
                {
                    imageService.removeById(list.get(i).getId());
                }
            }
        }*/
        /*if(res.length()==0) {
            return new Result().success(null);
        }else{

            res="第"+(res.charAt(res.length()-1)==',' ? res.substring(0,res.length()-1):res)+"张图片大小超出限制";
            return new Result().success(res);

        }*/
    }
    @RequestMapping("delImg")
    public Result delImg(@RequestBody Integer[] ids)
    {
        if(ids!=null && ids.length >0)
        {
            for(int i=0;i<ids.length;++i)
            {
                Image image = imageService.getById(ids[i]);
                String path = image.getPath();
                path = System.getProperty("user.dir")+"/noteImgs/"+path;
                System.out.println(path);
                deleteFile(path);

                imageService.removeById(ids[i]);
            }
        }
        return new Result().success(null);
    }

    public  boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除成功");
                return true;
            } else {
                System.out.println("删除失败");
                return false;
            }
        } else {
            System.out.println("文件不存在");
            return false;
        }
    }

}
