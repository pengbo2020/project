package com.java.project.utils;

import com.java.project.entity.Question;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class PdfTest {

    /*public static void main(String[] args) {
        PdfTest pdf = new PdfTest();
        pdf.createPdf(null,null);
    }*/
    //@SuppressWarnings({"unchecked", "rawtypes"})
    public Map createPdf(List<Question> questionList,String name,String fileName) {

        Map fileMap = new HashMap();
        // 获取模板

        HashMap<String, String> dataMap = new HashMap<String,String>();

        for(int i=0;i<33;++i)
        {
            Question question = questionList.get(i);
            String answer1 = question.getAnswer1() + "\n【解析】:" + question.getParse();
            String question1= question.getTitle()+ "\n\t" +
                    "A." + question.getOptionA() + "\n\t" +
                    "B." + question.getOptionB() + "\n\t" +
                    "C." + question.getOptionC() + "\n\t"+
                    "D." + question.getOptionD();
            question1 = question1.replaceAll("\n","</w:t><w:br/><w:t>");
            question1 = question1.replaceAll("\t","&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
            answer1 = answer1.replaceAll("\n","</w:t><w:br/><w:t>");
            dataMap.put(("q"+(i+1)),question1);
            dataMap.put(("a"+(i+1)),answer1);
        }

        for(int i=33; i< 38;++i)
        {
            Question question = questionList.get(i);
            String question1 = question.getTitle();
            String answer1 = question.getParse();
            question1 = question1 + "</w:t><w:br/><w:br/><w:br/><w:t>";
            question1.replaceAll("\n","\n\t");
            question1 = question1.replaceAll("\t","&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
            question1 = question1.replaceAll("\n","</w:t><w:br/><w:t>");
            answer1 = answer1.replaceAll("\n","</w:t><w:br/><w:t>");
            dataMap.put(("q"+(i + 1)),question1);
            dataMap.put(("a"+(i + 1)),answer1);

        }
        dataMap.put("title",name);

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/template/");

        try {
            Template template = configuration.getTemplate("1.ftl");
            template.setEncoding("UTF-8");

            /**
             * 这段是我为了测试pdf功能而增加上的-start
             */
            //将数据和ftl文件组装为xml文本
            StringWriter stringWriter = new StringWriter();
            BufferedWriter writer = new BufferedWriter(stringWriter);
            template.process(dataMap, writer);
            String xmlStr = stringWriter.toString();
            System.out.println("xmlStr:" + xmlStr);
            writer.close();
            //使用docx4j将xml文本加载为word文档对象
            ByteArrayInputStream in = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
            // Word2003XmlConverter.
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(in);
            Mapper nzt = wordMLPackage.getFontMapper();
            Map<String, PhysicalFont> fontMappingssss = nzt.getFontMappings();
            //使用docx4j将word文档转存为pdf文档
            String outputfilepath = "D:\\pdf\\"+ fileName +".pdf";   //再放到数据库里面
            FileOutputStream os = new FileOutputStream(new File(outputfilepath));
            FOSettings foSettings = Docx4J.createFOSettings();

            Mapper fontMapper = new IdentityPlusMapper();
            Map<String, PhysicalFont> physicalFonts = PhysicalFonts.getPhysicalFonts();
            Set<String> keySet = physicalFonts.keySet();
            //中文字体转换
            fontMapper.getFontMappings().put("Calibri", physicalFonts.get("Calibri"));
            fontMapper.getFontMappings().put("华文行楷", physicalFonts.get("STXingkai"));
            fontMapper.getFontMappings().put("隶书", physicalFonts.get("LiSu"));
            fontMapper.getFontMappings().put("宋体", physicalFonts.get("SimSun"));
            fontMapper.getFontMappings().put("微软雅黑", physicalFonts.get("Microsoft Yahei"));
            fontMapper.getFontMappings().put("黑体", physicalFonts.get("SimHei"));
            fontMapper.getFontMappings().put("楷体", physicalFonts.get("KaiTi"));
            fontMapper.getFontMappings().put("新宋体", physicalFonts.get("NSimSun"));
            fontMapper.getFontMappings().put("华文行楷", physicalFonts.get("STXingkai"));
            fontMapper.getFontMappings().put("华文仿宋", physicalFonts.get("STFangsong"));
            fontMapper.getFontMappings().put("宋体扩展", physicalFonts.get("simsun-extB"));
            fontMapper.getFontMappings().put("仿宋", physicalFonts.get("FangSong"));
            fontMapper.getFontMappings().put("仿宋_GB2312", physicalFonts.get("FangSong_GB2312"));
            fontMapper.getFontMappings().put("幼圆", physicalFonts.get("YouYuan"));
            fontMapper.getFontMappings().put("华文宋体", physicalFonts.get("STSong"));
            fontMapper.getFontMappings().put("华文中宋", physicalFonts.get("STZhongsong"));
            try {
                wordMLPackage.setFontMapper(fontMapper);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            foSettings.setWmlPackage(wordMLPackage);
            Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

            /**
             * 这段是我为了测试pdf功能而增加上的-end
             */
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Docx4JException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("hello");
        //fileMap.put(loan.getEnterpriseName()+"_投放通知书_告知书.doc", file);
        return fileMap;
    }


}

