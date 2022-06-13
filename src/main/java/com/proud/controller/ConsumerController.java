package com.proud.controller;

import com.proud.basic.ControllerBasic;
import com.proud.entity.ConsumerEntity;
import com.proud.exception.ConsumerException;
import com.proud.service.ConsumerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/consumer")
public class ConsumerController extends ControllerBasic {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @RequestMapping(value = "/sign/up" , method = RequestMethod.GET)
    protected ConsumerEntity signUp(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password,
            @RequestParam("code") String code) throws ConsumerException {

        return consumerService.signUp(code, mobile, password);
    }

    @RequestMapping(value = "/sign/in", method = RequestMethod.POST)
    protected String signIn(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        return "consumer login success";
    }
}
