package com.proud.basic;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

@Data
public abstract class EntityBasic {

    @IsAutoIncrement
    @IsKey
    @TableId(type = IdType.AUTO)
    @Column(comment = "ID")
    private Long id;

    @ColumnType(MySqlTypeConstant.DATETIME)
    @Column(comment = "创建时间", isNull = false, defaultValue = "now()")
    private Date createAt;

    @ColumnType(MySqlTypeConstant.DATETIME)
    @Column(comment = "删除时间", isNull = true, defaultValue = "NULL")
    private Date deleteAt;

    @ColumnType(MySqlTypeConstant.DATETIME)
    @Column(comment = "更新时间", isNull = false, defaultValue = "now()")
    private Date updateAt;
}
