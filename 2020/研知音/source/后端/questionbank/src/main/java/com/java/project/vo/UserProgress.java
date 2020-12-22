package com.java.project.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgress {
    Integer totalChapterQuestionNum;
    Integer totalUserQuestionNum;
}
