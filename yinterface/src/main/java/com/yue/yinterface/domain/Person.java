package com.yue.yinterface.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName person
 */
@TableName(value ="person")
@Data
public class Person implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "hour")
    private Integer hour;

    /**
     * 
     */
    @TableField(value = "message")
    private String message;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}