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
 * 知识点的描述内容
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="KnowledgeDesc对象", description="知识点的描述内容")
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "knowledge_basic的id")
    @TableField("knowledge_basic_id")
    private Integer knowledgeBasicId;

    @ApiModelProperty(value = "描述内容")
    @TableField("desc_name")
    private String descName;

    @ApiModelProperty(value = "具体内容")
    @TableField("desc_detail")
    private String descDetail;

    @TableField("creator")
    private String creator;

    @TableField("create_at")
    private String createAt;

    @TableField("update_at")
    private String updateAt;


}
