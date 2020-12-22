package com.java.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.entity.*;
import com.java.project.entity.Image;
import com.java.project.mapper.ChapterMapper;
import com.java.project.mapper.QuestionMapper;
import com.java.project.service.*;
import com.java.project.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class QuestionbankApplicationTests {

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    ExamService examService;

    @Autowired
    ExamQuestionService examQuestionService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ChapterQuestionService chapterQuestionService;

    @Autowired
    KnowledgeBasicService knowledgeBasicService;

    @Autowired
    KnowledgeDescService knowledgeDescService;

    @Autowired
    ChapterKnowledgeService chapterKnowledgeService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ImageService imageService;

    @Autowired
    NewsService newsService;



    public Integer cid;


    // @Test
    void contextLoads() {

    }

   // @Test
    public void test1() throws Exception {

        int pn=22;
        int pdfPageNum=29;
        int count =6;
        String tmp="";
        PDDocument pdDocument = PDDocument.load(new File("F:\\1.pdf"));
        if (pdDocument.isEncrypted()) {//加密
            System.out.println("pdDocument.isEncrypted");
            return;
        }
        PDPage page = pdDocument.getPage(30);//第一页
        PDFTextStripperByArea pdfTextStripper = new PDFTextStripperByArea();//区域文本剥离器
        pdfTextStripper.addRegion("region1", new Rectangle(0, 0, (int) page.getMediaBox().getWidth(), (int) page.getMediaBox().getHeight()));//区域大小
        pdfTextStripper.extractRegions(page);//设置
        pdfTextStripper.setSortByPosition(true);//排序
        String text = pdfTextStripper.getTextForRegion("region1");//剥离文本
        /*String[] strings = text.split("表 2");
        String[] s = strings[1].split("考点");
        String[] s1 = s[1].split("考题位置");
        System.out.println(s1[0].length());
        System.out.println(s1[0]);*/
        /*Pattern p = Pattern.compile("表 2([\\s\\S]*). 18 .");*/
        /*System.out.println(text);

        System.out.println("续表([\\s\\S]*)表 " + (count + 1));*/
        Pattern p = Pattern.compile("续表([\\s\\S]*)表 "+(count+1));  // 例如从续表到 表 4之间的内容
        Matcher m=p.matcher(text);
        if(m.find())
        {
            tmp=tmp+m.group(1);

        }
        System.out.println(tmp);
        tmp=null;
        count++;



        Pattern p1 = Pattern.compile("表 "+count+"([\\s\\S]*)表 "+(count+1));
        m=p1.matcher(text);
        while(m.find()){
            tmp=m.group(1);
            System.out.println(tmp);
            count++;
            m=Pattern.compile("表 "+count+"([\\s\\S]*)表 "+(count+1)).matcher(text);
        }
        System.out.println("表 " + count + "([\\s\\S]*). " + pn + " .");
        m=Pattern.compile("表 " + count + "([\\s\\S]*)。").matcher(text);
        if(m.find())
        {
            tmp=m.group(1);
            System.out.println(tmp);
            pn++;
        }
        System.out.println(count);
        System.out.println(pn);

        //System.out.println(text);
        pdDocument.close();
    }

    //@Test
    public  void test()
    {
        String  hi ="［简析】马克思主义哲学第一次明确提出：“全 部哲学，特别是近代哲学的重大的基本问题, 是思维和存在的关系问题。”哲学基本问题包 括两个方面的内容:其一，存在和思维究竟谁 是世界的本原，即物质和精神何者是第一性、 何者是第二性的问题;其二，思维能否正确认 识存在，即存在和思维有无同一性的问题。 马克思主义关于哲学基本问题的原理为划分 哲学中的基本派别确定了科学标准。根据对 哲学基本问题第一方面的不同回答，哲学可 划分为唯物主义和唯心主义两个对立的基本 派别。唯物主义把世界的本原归结为物质, 主张物质第一性，意识第二性，意识是物质的 产物;唯心主义把世界的本原归结为精神，主 张意识第一性，物质第二性,物质是意识的产 物。这就是唯物主义和唯心主义这两个专门 的哲学术语的特定的含义和确定的标准，A 正确。存在和思维是否具有同一性，是哲学 基本问题的第二方面的内容，对这个问题的 不同回答,是划分可知论和不可知论的标准, B错误。社会存在与社会意识的关系问题是 社会历史观的基本问题，是区分唯物史观与 唯心史观的标准，唯物史观认为社会存在决 定社会意识，唯心史观认为社会意识决定社 会存在,C错误。哲学除了回答世界的本原是 什么的问题，还要回答世界是怎样存在的问 题，即世界上的事物是联系的还是孤立的，是 发展的还是静止的。根据对这些问题的不同\n" +
                "回答，形成了辩证法和形而上学两种不同的观点,D错误。\n";

        Matcher m = Pattern.compile("简析.([\\s\\S]*\n[\\s\\S]*)\n").matcher(hi);
        if(m.find())
        {
            System.out.println(m.group(1).replaceAll("\n",""));
        }

    }

    //@Test
    public void test2()
    {
        /*List<Chapter> chapters = chapterMapper.selectList(null);
        chapters.forEach((chapter)->{
            System.out.println(chapter);
        });*/
        //examService.createExam(10);

    }

    //@Test
    public void test3()
    {

      new PdfTestOld().createPdf();

    }

    //@Test
    public void test00()
    {
        //2014 12 2013 19 2012 27 2011 37
        int [] examsId={12,19,27,37};
        int [] exams={74,75,76,77};
        for(int i=0;i<4;++i)
        {
            List<ExamQuestion> examQuestionList = examQuestionService.list(new QueryWrapper<ExamQuestion>().eq("exam_id", examsId[i]));
            if(examQuestionList!=null && examQuestionList.size()>16)
            {
                for (ExamQuestion examQuestion : examQuestionList) {
                    examQuestion.setExamId(exams[i]);
                    examQuestionService.save(examQuestion);
                }
            }else{
                System.out.println(examsId[i]);
            }
        }

    }
    //@Test
    public void test03()
    {

    }

   // @Transactional
   // @Test
    public void test01()
    {
        int pid=5;
        String [] chapterNames={"形势与政策","当代世界经济与政治"};

        /*for(int i=0;i<chapterNames.length;++i)
        {
            chapterService.save(new Chapter(null,pid,chapterNames[i]));
        }
*/
        List<Chapter> chapterList = chapterService.list(new QueryWrapper<Chapter>().eq("pid", pid));
       /* for (Chapter chapter : chapterList) {
            int cid = chapter.getId();*/
            List<Integer> allSingleId = questionMapper.selectAllSingleId();
            List<Integer> allMultiId = questionMapper.selectAllMultiId();
            List<Integer> allAnalysisId = questionMapper.selectAllAnalysisId();
            List<Integer> singleId =null;
            List<Integer> multiId = null;
            List<Integer> analyId =null;
           /* if(type==1) {
                singleId = RandomRangeUtil.getRandom(allSingleId.size(), 16);
                multiId = RandomRangeUtil.getRandom(allMultiId.size(), 17);
                analyId = RandomRangeUtil.getRandom(allAnalysisId.size(), 5);
            }else{
                singleId = RandomRangeUtil.getRandom(allSingleId.size(), 10);
                multiId = RandomRangeUtil.getRandom(allMultiId.size(), 5);
                analyId = new ArrayList<Integer>();
            }*/

            singleId = RandomRangeUtil.getRandom(allSingleId.size(), 9);
            multiId = RandomRangeUtil.getRandom(allMultiId.size(), 9);
            analyId = RandomRangeUtil.getRandom(allAnalysisId.size(), 1);
            System.out.println("*******************第" + 0 + "次开始" + "********************");
            System.out.println(allSingleId);
            System.out.println(allMultiId);
            System.out.println(allAnalysisId);
            System.out.println(singleId);
            System.out.println(multiId);
            System.out.println(analyId);
            for(int i=0;i<singleId.size();++i)
            {
                chapterQuestionService.save(new ChapterQuestion(null,11,allSingleId.get(singleId.get(i))));
            }
            for(int i=0;i<multiId.size();++i)
            {
                chapterQuestionService.save(new ChapterQuestion(null,11,allMultiId.get(multiId.get(i))));
            }
            for(int i=0;i<analyId.size();++i)
            {
                chapterQuestionService.save(new ChapterQuestion(null,11,allAnalysisId.get(analyId.get(i))));
            }

            System.out.println("*******************第" + 0 + "次结束" + "********************");

        //}



        //把这些数据都放到数据库里面去



    }

    //@Test
    public void test04()
    {

    }

   // @Test
    public void test02() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\politics\\1000\\1000题";
        File dir = new File(path);
        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){

                Chapter chapter = chapterService.getOne(new QueryWrapper<Chapter>().eq("name", file.getName()).eq("pid", 0));


                readFilesDir2(file,chapter.getId());
            }else{

                System.out.println(file.getAbsolutePath());
            }
        }

    }


    public  List<List<String>> readExcle(String fileName) throws Exception {

        System.out.println(this.cid);
        //new一个输入流
        FileInputStream inputStream = new FileInputStream(fileName);
        //new一个workbook
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //创建一个sheet对象，参数为sheet的索引
        XSSFSheet sheet = workbook.getSheetAt(0);
        //new出存放一张表的二维数组
        List<List<String>> allData = new ArrayList<List<String>>();

        for (Row row:sheet) {
            List<String> oneRow = new ArrayList<String>();
            //不读表头
            if(row.getRowNum()==0)
                continue;
            for (Cell cell : row) {
                cell.setCellType(cell.CELL_TYPE_STRING);
                oneRow.add(cell.getStringCellValue().trim());
            }
            allData.add(oneRow);
        }
        if(this.cid!=null) {
            for (int i = 0; i < allData.size(); i++) {
                if (allData.get(i) != null && allData.get(i).size() > 3 && allData.get(i).get(0) != null && allData.get(i).get(0).length() > 2 && allData.get(i).get(1) != null && allData.get(i).get(1).length() == 1) {
                    if (this.cid != null) {
                  /*  KnowledgeBasic knowledgeBasic=null;
                    KnowledgeBasic knowledgeBasic1 = knowledgeBasicService.getOne(new QueryWrapper<KnowledgeBasic>().eq("name", allData.get(i).get(0)));
                    if(knowledgeBasic1==null) {
                        knowledgeBasic = new KnowledgeBasic(null, allData.get(i).get(0), null, null, null);
                        knowledgeBasicService.save(knowledgeBasic);
                    }else{
                        knowledgeBasic=knowledgeBasic1;
                    }

                    List<ChapterKnowledge> list = chapterKnowledgeService.list(new QueryWrapper<ChapterKnowledge>().eq("chapter_id", this.cid).eq("knowledge_basic_id", knowledgeBasic.getId()));
                    if(!(list!=null && list.size()>0)) {
                        chapterKnowledgeService.save(new ChapterKnowledge(null, this.cid, knowledgeBasic.getId()));
                    }
                    String descName1="内容";
                    String descName2="重要程度";
                    if(knowledgeDescService.getOne(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id",knowledgeBasic.getId()).eq("desc_name",descName1))==null)
                    {
                        knowledgeDescService.save(new KnowledgeDesc(null,knowledgeBasic.getId(),descName1,allData.get(i).get(1),null,null,null));
                    }

                    if(allData.get(i).size()>2 && allData.get(i).get(2)!=null && allData.get(i).get(2).length()>2 && knowledgeDescService.getOne(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id",knowledgeBasic.getId()).eq("desc_name",descName2))==null)
                    {
                        knowledgeDescService.save(new KnowledgeDesc(null,knowledgeBasic.getId(),descName2,allData.get(i).get(2),null,null,null));
                    }*/
                        int type = Integer.parseInt(allData.get(i).get(1));

                        if (allData.get(i).size() >= 7 && allData.get(i).get(2) != null && allData.get(i).get(2).length() > 1 && allData.get(i).get(3) != null && allData.get(i).get(3).length() >= 1 && allData.get(i).get(4) != null && allData.get(i).get(4).length() >= 1 && allData.get(i).get(5) != null && allData.get(i).get(5).length() >= 1 && allData.get(i).get(6) != null && allData.get(i).get(6).length() >= 1) {
                            String parse=null;
                            if(allData.get(i).size()>7 && allData.get(i).get(7).length()>=1)
                            {
                                parse = allData.get(i).get(7);
                            }
                            Question question =null;
                            question = questionService.getOne(new QueryWrapper<Question>().eq("title", allData.get(i).get(0)));
                            if(question==null) {
                                question = new Question(null, allData.get(i).get(0), allData.get(i).get(2), allData.get(i).get(3), allData.get(i).get(4), allData.get(i).get(5), type, allData.get(i).get(6), null, parse, 0, null, null, null, null, null, null, null);
                                questionService.save(question);
                            }
                            ChapterQuestion chapterQuestion = chapterQuestionService.getOne(new QueryWrapper<ChapterQuestion>().eq("chapter_id", this.cid).eq("question_id", question.getId()));
                            if(chapterQuestion==null)
                            {
                                chapterQuestionService.save(new ChapterQuestion(null,this.cid,question.getId()));
                            }



                        }
                  /*  if(type==0 || type==1)
                    {
                        if(allData.get(i).size()>=7)
                        {
                            System.out.println(allData.get(i));
                        }
                    }else{
                        if(allData.get(i).size()>=2)
                        {
                            System.out.println(allData.get(i));
                        }
                    }*/


                    }


                } else {
                    // System.out.println(allData.get(i));
                    if(allData.get(i).size()>=2 && allData.get(i).get(0)!=null && allData.get(i).get(0).length()>=2 && allData.get(i).get(1)!=null && allData.get(i).get(1).length()>=2)
                    {
                        Question question =null;
                        question = questionService.getOne(new QueryWrapper<Question>().eq("title", allData.get(i).get(0)));
                        if(question==null) {
                            question = new Question(null, allData.get(i).get(0), null, null, null, null, 2, null, null,  allData.get(i).get(1), 0, null, null, null, null, null, null, null);
                                questionService.save(question);
                            }
                            ChapterQuestion chapterQuestion = chapterQuestionService.getOne(new QueryWrapper<ChapterQuestion>().eq("chapter_id", this.cid).eq("question_id", question.getId()));
                            if(chapterQuestion==null) {
                                chapterQuestionService.save(new ChapterQuestion(null, this.cid, question.getId()));
                            }

                    }
                }

            }
        }
        //关闭workbook
        workbook.close();
        return allData;
    }


    public  void readFilesDir2(File dir,Integer pid) throws Exception {

        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){

                Chapter chapter = chapterService.getOne(new QueryWrapper<Chapter>().eq("name", file.getName()).eq("pid", pid));
                //System.out.println(chapter);
                if(chapter!=null) {
                  //  System.out.println("到这了");
                    //System.out.println(chapter);
                    readFilesDir2(file, chapter.getId());
                }else{
                    /*System.out.println("到这了2222222");
                    System.out.println(file.getName());*/
                    Chapter chapter1 = new Chapter(null, pid, file.getName());
                    chapterService.save(chapter1);
                    //System.out.println(chapter1);
                    readFilesDir2(file, chapter1.getId());

                    //readFilesDir2(file,0);
                }
            }
            else{

                // System.out.println(file);
                //readExcel2(file);
               // System.out.println(file.getAbsolutePath());
                this.cid=pid;
                //System.out.println(this.cid);

                readExcle(file.getAbsolutePath());
            }
        }

    }

    public List readExcel2(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
                        System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

      //@Test
    public void test05()
    {
        List<KnowledgeBasic> knowledgeBasicList = knowledgeBasicService.list(new QueryWrapper<KnowledgeBasic>().gt("id", 71));
        for (KnowledgeBasic knowledgeBasic : knowledgeBasicList) {
            Integer id = knowledgeBasic.getId();
            KnowledgeDesc knowledgeDesc = knowledgeDescService.getOne(new QueryWrapper<KnowledgeDesc>().eq("knowledge_basic_id", id).eq("desc_name", "内容"));
            String data=knowledgeBasic.getName()+"。"+knowledgeDesc.getDescDetail();
            String writeFile = "E:\\javaprojects\\tts\\src\\main\\resources\\audio\\"+id+".mp3";
            System.out.println("******************************************************开始****************************");
            System.out.println(knowledgeBasic);
            System.out.println(data);
            System.out.println(writeFile);
            Tts.convertMP3(data,writeFile);
            imageService.save(new Image(null,id,id+".mp3",null,1));
            System.out.println("******************************************************结束****************************");

        }


    }

    //@Test
    public void test06()
    {
        List<News> newsList = newsService.list();
        for (News news : newsList) {
            Integer id = news.getId();

            String writeFile = "E:\\javaprojects\\questionbank\\newsaudio\\"+id+".mp3";
            String data = news.getTitle()+"。"+news.getContent();
            System.out.println("******************************************************开始****************************");
            System.out.println(writeFile);
            Tts.convertMP3(data,writeFile);
            news.setPath(id+".mp3");
            newsService.updateById(news);
            System.out.println("******************************************************结束****************************");


        }
    }

    //@Test
    void test07()
    {
        PdfTest pdfTest = new PdfTest();
        List<Exam> examList = examService.list(new QueryWrapper<Exam>().ne("type", 2));

       for(int i=1;i<examList.size();++i)
       {
           Exam exam = examList.get(i);
           List<Question> questions = questionService.getQuestions(exam.getId());
           /*for (Question question : questions) {
               System.out.println(question);
           }*/

          if(exam.getType()==0) {
              pdfTest.createPdf(questions, exam.getName(), exam.getPath());
          }else{
              pdfTest.createPdf(questions, exam.getPath(), exam.getPath());
          }
//           System.out.println(examList.get(i));

       }



    }
   //@Test
    void test08() throws Exception {

       List<List<String>> lists = ReadExcel.readExcle("C:\\Users\\Administrator\\Desktop\\politics\\时事政治(1).xlsx");
       String []timeStamp={"2020-10-28 21:59:06","2020-10-27 20:59:06","2020-10-27 21:54:06","2020/10/27","2020/10/27","2020/10/27","2020/10/27","2020/10/27","2020/10/27",
           "2020/10/27","2020/10/26","2020/10/26","2020/10/26","2020/10/26","2020/10/26","2020/10/24","2020/10/24","2020/10/22","2020/10/22",
            "2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22","2020/10/22"};
       for(int i=0;i<lists.size();++i)
       {
           List<String> list = lists.get(i);
           System.out.println("******************************************************开始****************************");
           String title= list.get(0) ;
           String name = list.get(1);
           String content = list.get(2);
           String time= timeStamp[i];
         /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           time = new Long(sdf.parse(time).getTime()).toString();
           System.out.println(sdf.parse(time).getTime());
           System.out.println(time);*/
           if(newsService.getOne(new QueryWrapper<News>().eq("title",title))==null) {
                News news= new News(null, title, name, content, time, 0, null);
               newsService.save(news);
               String writeFile = "E:\\javaprojects\\questionbank\\newsaudio\\"+news.getId()+".mp3";
               Tts.convertMP3(title+"。"+content,news.getId()+".mp3");
               news.setPath(news.getId()+".mp3");
               newsService.updateById(news);

           }



           System.out.println("******************************************************结束****************************");
       }


   }


    @Test
    public void dateToStamp() throws Exception {
        List<News> newsList = newsService.list(new QueryWrapper<News>().gt("id", 30));
        for (News news : newsList) {
            int hNum = new Random().nextInt((24 - 10) ) + 10;
            int mNum = new Random().nextInt((60 - 11) ) + 11;
            int sNum = new Random().nextInt((60 - 1) ) + 1;
            String h = Integer.toString(hNum);
            String m = Integer.toString(mNum);
            String s = Integer.toString(sNum);
            String sff=" "+h+":"+m+":"+s;
            System.out.println(sff);


            String str = news.getPublishedAt()+sff;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = simpleDateFormat.parse(str);
            long ts = date.getTime();
            System.out.println(ts);
            System.out.println(Long.toString(ts));
            news.setPublishedAt(Long.toString(ts));
            newsService.updateById(news);
        }
        //String str = "2019/03/13 13:54:00";


    }






}
