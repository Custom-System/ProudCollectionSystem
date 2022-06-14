package com.proud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proud.entity.ConsumerEntity;
import com.proud.exception.ConsumerException;
import com.proud.pkg.server.WebResponse;

public interface ConsumerService extends IService<ConsumerEntity> {


    /**
     * @param mobileCode   手机号国家代码
     * @param mobileNumber 手机号码
     * @return
     */
    public WebResponse sendMobileNumberSignUpVerificationCode(String mobileCode, String mobileNumber) throws ConsumerException;

    /**
     * @param mobileCode   手机号国家代码
     * @param mobileNumber 手机号码
     * @param password     登录密码
     * @return 注册成功的用户信息
     */
    public WebResponse signUp(String mobileCode, String mobileNumber, String password, String verificationCode) throws ConsumerException;

    /**
     * @param email    邮箱
     * @param password 登录密码
     * @return 登录成功的用户信息
     */
    public WebResponse signInWithEmail(String email, String password);

    /**
     * @param email            邮箱
     * @param verificationCode 邮箱验证码
     * @return 登录成功的用户信息
     */
    public WebResponse signInWithEmailVerificationCode(String email, String verificationCode);

    /**
     * @param mobileNumber 手机号码
     * @param password     登录密码
     * @return 登录成功的用户信息
     */
    public WebResponse signInWithMobileNumberPassword(String mobileNumber, String password);

    /**
     * @param mobileCode       手机号国家代码
     * @param mobileNumber     手机号码
     * @param verificationCode 手机验证码
     * @return 登录成功的用户信息
     */
    public WebResponse signInWithMobileVerificationCode(String mobileCode, String mobileNumber, String verificationCode) throws ConsumerException;
}
