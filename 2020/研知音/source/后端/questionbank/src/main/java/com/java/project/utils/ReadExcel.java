package com.java.project.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.project.controller.QuestionController;
import com.java.project.entity.Chapter;
import com.java.project.service.ChapterService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReadExcel {


    //obj.readFilesDir("C:\\Users\\Administrator\\Desktop\\politics\\知识点");
    // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
    //File file = new File("D:/1.xlsx");
    //List excelList = obj.readExcle("D:/1.xlsx");
    //List<List<String>> lists = obj.readExcle("C:\\Users\\Administrator\\Desktop\\新建文件夹\\马克思主义是关于无产阶级和人类解放的科学.xlsx");
       /* for (List<String> list : lists) {
            for (String s : list) {
                System.out.println(s);
            }
        }*/
    //System.out.println("list中的数据打印出来");
     /*   for (int i = 0; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j));
            }
            System.out.println();
        }*/

   /* public static void main(String[] args) throws Exception {
        ReadExcel obj = new ReadExcel();

         String path = "C:\\Users\\Administrator\\Desktop\\politics\\知识点";
        File dir = new File(path);
        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){
                System.out.println(file.getName());

               obj.readFilesDir2(file,0);
            }else{


                System.out.println(file.getAbsolutePath());
            }
        }

    }*/

    /**
     * 读取文件夹下的所有文件的内容
     * @param dir
     */
    public static void readFilesDir2(File dir,Integer pid){

        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){

                readFilesDir2(file,pid+1);
             }
            else{
                System.out.println(pid);
                System.out.println(file.getAbsolutePath());
            }
        }

    }
    public static void readFilesDir(String path){
        LinkedList<File> Dirlist = new LinkedList<File>();
        LinkedList<String> Filelist = new LinkedList<String>();
        File dir = new File(path);
        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){
                Dirlist.add(file);
            }else{
                //处理文件内容
                Filelist.add(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
            }
        }

        File temp;
        while(!Dirlist.isEmpty()){
            temp = Dirlist.removeFirst();
            if(temp.isDirectory()){
                files = temp.listFiles();
                if(files == null) continue;
                for(File file : files){
                    if(file.isDirectory()){
                        Dirlist.add(file);
                    }else{
                        //处理文件内容
                        Filelist.add(file.getAbsolutePath());
                        System.out.println(file.getAbsolutePath());
                    }
                }
            }else{
                //处理文件内容,这种情况好像不会发生
                System.out.println("-------------");
            }
        }
    }

    public static List<List<String>> readExcle(String fileName) throws Exception {

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

       /* for (int i = 0; i < allData.size(); i++) {
            System.out.println(allData.get(i));
        }*/
        //关闭workbook
        workbook.close();
        return allData;
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
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
}