package com.java.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 题目
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Question对象", description="题目")
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("option_a")
    private String optionA;

    @TableField("option_b")
    private String optionB;

    @TableField("option_c")
    private String optionC;

    @TableField("option_d")
    private String optionD;

    @ApiModelProperty(value = "0 选择题 1 多选题 2 分析题 ")
    @TableField("type")
    private Integer type;

    @TableField("answer1")
    private String answer1;

    @TableField("answer2")
    private String answer2;

    @ApiModelProperty(value = "解析")
    @TableField("parse")
    private String parse;

    @ApiModelProperty(value = "难度")
    @TableField("diff")
    private Integer diff;

    @TableField("md5")
    private String md5;

    @ApiModelProperty(value = "关联的知识点，以便前端方便使用")
    @TableField("knowledge")
    private String knowledge;

    @TableField("year")
    private Integer year;

    @ApiModelProperty(value = "是否来源于试卷")
    @TableField("source")
    private Integer source;

    @ApiModelProperty(value = "是否有子题，分析题")
    @TableField("hasSubtheme")
    private Integer hasSubtheme;

    @ApiModelProperty(value = "是否常规题，如果选择题无法正常提取标准答案或者选项，有小题的答题无法正常提取小题，则isNormal为0，否则为1")
    @TableField("isNormal")
    private Integer isNormal;

    @ApiModelProperty(value = "是否匹配章节知识点，1匹配，0不匹配")
    @TableField("isKnow")
    private Integer isKnow;


}
