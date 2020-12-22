package com.java.project.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class Base64ToImage {
    /**
     * base64字符串转写为文件
     *
     * @param base64ImgData
     * @param filePath
     * @throws IOException
     */
    public static void convertBase64DataToImage(String base64ImgData, String filePath) throws IOException {
        Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64ImgData), StandardOpenOption.CREATE);
    }

    /**
     * 将文件转写为base64字符串
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String convertImageToBase64Data(String filePath) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * 判断图片base64字符串的文件格式
     *
     * @param base64ImgData
     * @return
     */
    public static String checkImageBase64Format(String base64ImgData) {
        byte[] b = Base64.getDecoder().decode(base64ImgData);
        String type = "";
        if (0x424D == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "bmp";
        } else if (0x8950 == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "png";
        } else if (0xFFD8 == ((b[0] & 0xff) << 8 | (b[1] & 0xff))) {
            type = "jpg";
        }
        return type;
    }


}
