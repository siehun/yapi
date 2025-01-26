package com.yue.yinterface.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 笑话表
 * @TableName funny
 */
@TableName(value ="funny")
@Data
public class Funny implements Serializable {
    /**
     * 笑话
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 热度
     */
    @TableField(value = "top")
    private Integer top;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}