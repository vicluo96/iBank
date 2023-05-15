package com.group3.brokebank.mapper;

import com.group3.brokebank.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserMapper {

    public User login(Map<String,Object> paramMap);
    public int create(User user);
    public Double query(int id);
    public int updateBalance(int id, Double balance);
}
