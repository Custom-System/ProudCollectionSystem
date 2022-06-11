package com.proud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("consumer")
public class ConsumerEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
}
