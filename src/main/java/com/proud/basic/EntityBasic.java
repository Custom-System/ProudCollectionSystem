package com.proud.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;


public abstract class EntityBasic {

    @IsAutoIncrement
    @IsKey
    @TableId(type = IdType.AUTO)
    @Column(comment = "ID")
    private Long id;
}
