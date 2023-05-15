package com.group3.brokebank.controller;

import com.group3.brokebank.entity.User;
import com.group3.brokebank.service.UserService;
import com.group3.brokebank.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/create")
    public Response create(@RequestBody User user) {
        user.setBalance(0);
        userService.create(user);
        return Response.ok(user);
    }
    @PostMapping("/query")
    public Response query(@RequestBody Map<String,String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        Double balance = userService.query(id);
        return Response.ok(balance);
    }

    @PostMapping("/deposit")
    public Response deposit(@RequestBody Map<String,String> map) {
        Integer id = Integer.parseInt(map.get("id"));
//      Check user has privilege to deposit id
//        User user = (User) request.getAttribute("user");
//        if(user.getId() !=  id){
//            throw new CustomException("Invalid Id");
//        }
        Double balance = userService.deposit(id, map.get("amount"));
        return Response.ok(balance);
    }

    @PostMapping("/withdrawal")
    public Response withdrawal(@RequestBody Map<String,String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        Double balance = userService.withdrawal(id, map.get("amount"));
        return Response.ok(balance);
    }

}
