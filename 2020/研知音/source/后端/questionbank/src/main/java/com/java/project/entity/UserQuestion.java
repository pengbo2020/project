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
 * 用户做过的题目，是有答案的
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserQuestion对象", description="用户做过的题目，是有答案的")
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("question_id")
    private Integer questionId;

    @TableField("answer1")
    private String answer1;

    @TableField("answer2")
    private String answer2;

    @TableField("answer3")
    private String answer3;

    @ApiModelProperty(value = "是否被收藏")
    @TableField("collect")
    private Integer collect;

    @ApiModelProperty(value = "是否做错了")
    @TableField("wrong")
    private Integer wrong;

    @ApiModelProperty(value = "是否做笔记了")
    @TableField("mark")
    private Integer mark;

    @TableField("create_at")
    private String createAt;

    @TableField("exam_id")
    private Integer examId;

}
