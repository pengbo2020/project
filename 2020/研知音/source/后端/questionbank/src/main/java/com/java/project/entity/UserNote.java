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
 * 用户笔记
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserNote对象", description="用户笔记")
@AllArgsConstructor
@NoArgsConstructor
public class UserNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "是否是题的笔记")
    @TableField("type")
    private Integer type;

    @TableField("create_at")
    private String createAt;

    @TableField("update_at")
    private String updateAt;

    @ApiModelProperty(value = "逻辑删除")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "问题id")
    @TableField("question_id")
    private Integer questionId;


}
