package com.java.project.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionNum {

    private Integer id;

    private Integer pid;

    private String name;

    private Integer cQnum;

    private Integer uQnum;
}
