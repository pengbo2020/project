package com.java.project.vo;

import com.java.project.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterKnowledgeVo {
    Chapter chapter;
    List<Chapter> childChapterList;
}
