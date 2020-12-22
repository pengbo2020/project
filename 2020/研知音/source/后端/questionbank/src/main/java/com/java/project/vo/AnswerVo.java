package com.java.project.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVo{
    Integer qid;
    String title;
    Integer type;
    List<Option> optionList;
    String answer;
    String userAnswer;
    Boolean userFavor;
    String explain;
    String knowledge;
    Note note;
}
