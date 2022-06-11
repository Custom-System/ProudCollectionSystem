package com.proud.controller;

import com.proud.basic.ControllerBasic;
import com.proud.client.Katsubushi;
import com.proud.entity.ConsumerEntity;
import com.proud.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/consumer")
public class ConsumerController extends ControllerBasic {

    @Autowired
    private Katsubushi katsubushi;
    @Autowired
    private ConsumerService consumerService;
    @RequestMapping(value = "/sign/up" , method = RequestMethod.GET)
    protected String signUp(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password,
            @RequestParam("code") String code) throws IOException {

        katsubushi.GetId("test", null);

        ConsumerEntity consumerEntity = new ConsumerEntity();
        consumerEntity.setName(mobile);
        consumerService.save(consumerEntity);

        return consumerEntity.toString();
    }

    @RequestMapping(value = "/sign/in", method = RequestMethod.POST)
    protected String signIn(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        return "consumer login success";
    }
}
