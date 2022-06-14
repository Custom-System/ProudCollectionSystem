package com.proud.controller;

import com.proud.basic.ControllerBasic;
import com.proud.exception.ConsumerException;
import com.proud.pkg.server.WebResponse;
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
    protected WebResponse signUpSms(
            @RequestParam("mCode") String mobileCode,
            @RequestParam("mNumber") String mobileNumber) throws ConsumerException {

        return consumerService.sendMobileNumberSignUpVerificationCode(mobileCode, mobileNumber);
    }

    @RequestMapping(value = "/sign/up" , method = RequestMethod.POST)
    protected WebResponse signUp(
            @RequestParam("mCode") String mobileCode,
            @RequestParam("mNumber") String mobileNumber,
            @RequestParam("pwd") String password,
            @RequestParam("smsCode") String verificationCode) throws ConsumerException {

        return consumerService.signUp(mobileCode, mobileNumber, password, verificationCode);
    }

    @RequestMapping(value = "/sign/in/mobile/password", method = RequestMethod.POST)
    protected WebResponse signIn(
            @RequestParam("mNumber") String mobileNumber,
            @RequestParam("password") String password) {

        return consumerService.signInWithMobileNumberPassword(mobileNumber, password);
    }
}
