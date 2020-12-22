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
 * 试卷
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Exam对象", description="试卷")
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "0 智能组卷 1 快速练习")
    @TableField("type")
    private Integer type;

    @TableField("name")
    private String name;

    @ApiModelProperty(value = "存放的是生成的pdf试卷的位置")
    @TableField("path")
    private String path;

    @TableField("create_at")
    private String createAt;


}
