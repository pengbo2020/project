package com.java.project.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRangeUtil {
    private static final Random RANDOM = new Random();

    public static int randInt(int min, int max) {
        int randomNum = RANDOM.nextInt((max - min) ) + min;
        return randomNum;
    }

    public static List<Integer> getRandom(int range, int num) {
        int split = range / num;
        ArrayList<Integer> list = new ArrayList<>(num);
        if (range > num) {
            for (int i = 0; i <= (range - split); i = i + split) {
              /*  System.out.println(i);
                System.out.println(split);
                Integer temp = randInt(i, i + split);*/
                list.add(randInt(i, i + split));
            }
        }
        return list;
    }


}


