package com.java.project.vo;

import com.java.project.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterAndNum {
    Chapter chapter;
    List<Chapter> childChapterList;
    List<Integer> cknumList;
    List<Integer> uknumList;
}
