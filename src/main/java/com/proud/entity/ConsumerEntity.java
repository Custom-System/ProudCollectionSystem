package com.proud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proud.basic.EntityBasic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("consumer")
public class ConsumerEntity extends EntityBasic {

    private String mobileCode;

    private String mobileNumber;

    private String email;

    private String nickname;

    @JsonIgnore
    private String password;
}
