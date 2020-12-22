package com.java.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 子问题，分析题
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Subquestion对象", description="子问题，分析题")
public class Subquestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("question_id")
    private Integer questionId;

    @ApiModelProperty(value = "题干")
    @TableField("stem")
    private String stem;

    @TableField("answer1")
    private String answer1;

    @TableField("answer2")
    private String answer2;

    @TableField("parse1")
    private String parse1;

    @TableField("parse2")
    private String parse2;

    @TableField("diff")
    private Float diff;

    @TableField("know")
    private String know;


}
