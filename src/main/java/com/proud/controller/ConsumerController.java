package com.proud.controller;

import com.proud.basic.ControllerBasic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController extends ControllerBasic {

    @RequestMapping(value = "/sign/up" , method = RequestMethod.POST)
    protected String signUp(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password,
            @RequestParam("code") String code) {

        return "register consumer success";
    }

    @RequestMapping(value = "/sign/in", method = RequestMethod.POST)
    protected String signIn(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        return "consumer login success";
    }
}
