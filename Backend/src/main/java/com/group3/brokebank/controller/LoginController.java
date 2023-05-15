package com.group3.brokebank.controller;

import com.group3.brokebank.entity.User;
import com.group3.brokebank.utils.jwt.JWT;
import com.group3.brokebank.service.UserService;
import com.group3.brokebank.utils.Maps;
import com.group3.brokebank.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Response login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
//        User user=userService.login(username,password);
        User user = userService.SQLDirectLogin(username, password);
        if(user!=null){
            String token= JWT.sign(user);
            return Response.ok(Maps.build().put("token",token).put("user",user).getMap());
        }else{
            return Response.fail("Username or password is wrong");
        }
    }
}
