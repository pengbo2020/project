package com.java.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcj
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userHead")
    private String userHead;

    @TableField("userName")
    private String userName;

    @TableField("userGender")
    private String userGender;

    @TableField("userNewLogin")
    private String userNewLogin;

    @TableField("userCity")
    private String userCity;

    @TableField("userProvince")
    private String userProvince;

    @TableField("userOpenid")
    private String userOpenid;


}
