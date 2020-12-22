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
 * 用户做过的试卷
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserExam对象", description="用户做过的试卷")
@AllArgsConstructor
@NoArgsConstructor
public class UserExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("exam_id")
    private Integer examId;

    @ApiModelProperty(value = "可以当作做试卷时的开始时间")
    @TableField("create_at")
    private String createAt;

    @ApiModelProperty(value = "在做试卷时的剩余时间")
    @TableField("remainingTime")
    private Integer remainingTime;


}
