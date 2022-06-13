package com.proud.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public abstract class EntityBasic {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Date createAt;

    private Date deleteAt;

    private Date updateAt;
}
