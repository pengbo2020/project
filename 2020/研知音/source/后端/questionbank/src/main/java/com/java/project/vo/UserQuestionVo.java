package com.java.project.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.java.project.entity.KnowledgeBasic;
import com.java.project.entity.Question;
import com.java.project.entity.UserNote;
import com.java.project.entity.UserQuestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionVo {

    private Question question;

    private UserQuestion userQuestion;

    private List<KnowledgeBasic> knowledgeBasicList;

    private UserNote userNote;

}
