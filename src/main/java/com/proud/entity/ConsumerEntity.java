package com.proud.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.proud.basic.EntityBasic;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("consumer")
public class ConsumerEntity extends EntityBasic {

    @Column(comment = "手机号码国际区号", isNull = false, length = 16)
    private int mobileCode;

    @Column(comment = "手机号码", isNull = false, length = 64)
    private String mobileNumber;

    @Column(comment = "邮箱", isNull = true)
    private String email;

    @Column(comment = "昵称", isNull = false, length = 32)
    private String nickname;

    @Column(comment = "登录密码", isNull = false, length = 1024)
    private String password;
}
