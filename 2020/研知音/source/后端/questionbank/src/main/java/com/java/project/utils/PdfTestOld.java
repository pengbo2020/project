package com.java.project.utils;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PdfTestOld {


    //@SuppressWarnings({"unchecked", "rawtypes"})
    public Map createPdf() {

        Map fileMap = new HashMap();
    /*    for (Loan loan : loanList) {
            // 创建数据
            String bankParentName = "";
//			List<SysOrg> orgByOrgId = service.getOrgByOrgId(loan.getBankId());
//			if (orgByOrgId!=null && orgByOrgId.size()>0) {
//				String orgPathname = orgByOrgId.get(0).getOrgPathname();
//				if (orgPathname.indexOf("/", 1)==-1) {
//					bankParentName = orgPathname.substring(1);
//				}else {
//					bankParentName = orgPathname.substring(1,orgPathname.indexOf("/", 1));
//				}
//			}

            // 	银行名称
            String bankName = loan.getBankName();
            // 	企业名称
            String enterpriseName = loan.getEnterpriseName();
            // 	贷款编号
//			String loanNumber = loan.getLoanNumber();
            String loanNumber = loan.getUpdator();
            // 会议方案期数
            Long meetingPlanNper = loan.getMeetingPlanNper();
            // 会议方案金额
            BigDecimal meetingPlanAmount = loan.getMeetingPlanAmount().multiply(new BigDecimal(10000));
            //融资平台企业类别
            String businessCategory = loan.getBusinessCategory();
            //信用贷款
            BigDecimal credit;
            //索赔金额
            BigDecimal claimAmount;
            if ("第一类".equals(businessCategory)) {
                credit = new BigDecimal(0);
                claimAmount =new BigDecimal(0);
            }else if ("第二类".equals(businessCategory)) {
                credit = meetingPlanAmount.multiply(new BigDecimal(0.6667));
                claimAmount = meetingPlanAmount.multiply(new BigDecimal(0.3333));
            }else if ("第三类".equals(businessCategory)) {
                credit = meetingPlanAmount.multiply(new BigDecimal(0.95));
                claimAmount = meetingPlanAmount.multiply(new BigDecimal(0.005));
            }else {
                credit = new BigDecimal(0);
                claimAmount =new BigDecimal(0);
            }

            Map dataMap = new HashMap();
            dataMap.put("arg0", bankParentName);
            dataMap.put("arg1", bankName);
            dataMap.put("arg2", enterpriseName);
            dataMap.put("arg3", loanNumber);
            dataMap.put("arg4", ConvertNumberToUpper.toChinese(meetingPlanNper+""));
            dataMap.put("arg5", ConvertNumberToUpper.digitUppercase(meetingPlanAmount.doubleValue()));
            if (credit.intValue()==0) {
                dataMap.put("arg6", "___________");
            }else {
                dataMap.put("arg6", ConvertNumberToUpper.digitUppercase(credit.doubleValue()));
            }
            if (claimAmount.intValue()==0) {
                dataMap.put("arg7", "___________");
            }else {
                dataMap.put("arg7", ConvertNumberToUpper.digitUppercase(claimAmount.doubleValue()));
            }*/

//			Map dataMap = new HashMap();
//			dataMap.put("bankName", loan.getBankName());
//			dataMap.put("loanNumber", loan.getLoanNumber());
//			dataMap.put("enterpriseName", loan.getEnterpriseName());
//			dataMap.put("applicationSituationAmount", ConvertNumberToUpper.digitUppercase(loan.getApplicationSituationAmount()*10000));
//			dataMap.put("meetingPlanAmount", ConvertNumberToUpper.digitUppercase(loan.getMeetingPlanAmount()*10000));
//			dataMap.put("meetingPlanNper", ConvertNumberToUpper.toChinese(loan.getMeetingPlanNper()+""));
        // 获取模板
        Map dataMap = new HashMap();
        String question="中国历史上第一部具有资产阶级共和国宪法性质的法典是\n" +
                "A.《钦定宪法大纲》\tB.《中华民国临时约法》\n" +
                "C.《中华民国约法》\tD.《训政纲领》";

        for(int i=0;i<33;++i)
        {
            dataMap.put(("p"+(i+1)),question);
        }
        question="结合材料回答问题：\n" +
                "材料1\n" +
                "近代以后，争取民族独立、人民解放和实现国家富强、人民幸福就成为中国人民的历史任务。 在旧式的农民战争走到尽头，不触动封建根基的自强运动和改良主义屡屡碰壁，资产阶级革命派领 导的革命和西方资本主义的其他种种方案纷纷破产的情况下，十月革命一声炮响，为中国送来了马 克思列宁主义，给苦苦探寻救亡图存出路的中国人民指明了前进方向、提供了全新选择。\n" +
                "——习近平2018年5月4日在纪念马克思诞辰200周年大会上的讲话 材料2\n" +
                "马克思主义不仅深刻改变了世界，也深刻改变了中国。\n" +
                "——习近平2018年5月4日在纪念马克思诞辰200周年大会上的讲话\n" +
                "(1)十月革命为什么会给中国“送来了马克思列宁主义”，为中国人民“提供了全新选择”？\n" +
                "(2)如何理解马克思主义“也深刻改变了中国”？";
        for(int i=0;i<4;++i)
        {
            dataMap.put(("p"+(i+1)),question);
        }

        File file1 = new File("E:\\javaprojects\\questionbank\\src\\main\\resources\\template");
        System.out.println(file1.getAbsolutePath());
        if(file1.exists())
        {
            System.out.println("hello");
        }else{
            System.out.println("world");
        }


        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/template/");
        String name = "temp" + UUID.randomUUID() + ".doc";

        File file = new File(name);
        try {
            Template template = configuration.getTemplate("0.ftl");
            template.setEncoding("UTF-8");

				/*Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), "UTF-8"));
				template.process(dataMap, out);
				out.close();*/
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
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(in);
            Mapper nzt = wordMLPackage.getFontMapper();
            Map<String, PhysicalFont> fontMappingssss = nzt.getFontMappings();
            //使用docx4j将word文档转存为pdf文档
            String outputfilepath = "D:\\flv\\test.pdf";
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
            // Docx4J.toPDF(wordMLPackage, new FileOutputStream(new File(outputfilepath)));
            //Word转PDF
				/*WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File("abc.docx"));
				Mapper fontMapper = new IdentityPlusMapper();
				// fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
				mlPackage.setFontMapper(fontMapper);
				OutputStream os = new java.io.FileOutputStream("abc.pdf");
				FOSettings foSettings = Docx4J.createFOSettings();
				foSettings.setWmlPackage(mlPackage);
				Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL); */

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
        //fileMap.put(loan.getEnterpriseName()+"_投放通知书_告知书.doc", file);
        return fileMap;
    }



}

