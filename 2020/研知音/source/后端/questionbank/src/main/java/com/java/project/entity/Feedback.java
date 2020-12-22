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
 * 
 * </p>
 *
 * @author zcj
 * @since 2020-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Feedback对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("bug_id")
    private Integer bugId;

    @ApiModelProperty(value = "0,question 1 知识点)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "反馈内容")
    @TableField("content")
    private String content;

    @TableField("create_at")
    private String createAt;

    @ApiModelProperty(value = "(0 已经查看,1 未查看)")
    @TableField("checked")
    private Integer checked;

    @ApiModelProperty(value = "(0 已经解决,1 未解决)")
    @TableField("solved")
    private Integer solved;


}
