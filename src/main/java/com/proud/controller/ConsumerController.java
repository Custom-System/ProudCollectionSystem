package com.proud.controller;

import com.proud.basic.ControllerBasic;
import com.proud.entity.ConsumerEntity;
import com.proud.exception.ConsumerException;
import com.proud.service.ConsumerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController extends ControllerBasic {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @RequestMapping(value = "/sign/up/sms", method = RequestMethod.GET)
    protected void signUpSms(
            @RequestParam("mCode") String mobileCode,
            @RequestParam("mNumber") String mobileNumber) throws ConsumerException {

        consumerService.sendMobileNumberSignUpVerificationCode(mobileCode, mobileNumber);
    }

    @RequestMapping(value = "/sign/up" , method = RequestMethod.GET)
    protected ConsumerEntity signUp(
            @RequestParam("mCode") String mobileCode,
            @RequestParam("mNumber") String mobileNumber,
            @RequestParam("pwd") String password,
            @RequestParam("smsCode") String verificationCode) throws ConsumerException {

        return consumerService.signUp(mobileCode, mobileNumber, password, verificationCode);
    }

    @RequestMapping(value = "/sign/in", method = RequestMethod.POST)
    protected String signIn(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        return "consumer login success";
    }
}
