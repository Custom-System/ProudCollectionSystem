package com.proud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author
 * @version 1.0
 * @date 2022/6/10 22:43
 */
@Data
@TableName("consumer")
public class ConsumerEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
}
