package com.java.project.vo;

import com.java.project.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class AddQuestionVo {

    Integer pid;
    Integer id;
    Integer type;
    List<Question> questions;
}
