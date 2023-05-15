package com.group3.brokebank.service;

import com.group3.brokebank.entity.User;
import com.group3.brokebank.utils.exception.CustomException;
import com.group3.brokebank.mapper.UserMapper;
import com.group3.brokebank.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ParamService paramService;

    public User login(String username, String password){
        return userMapper.login(Maps.build().put("username",username).put("password",password).getMap());
    }
    public int create(User user){
        return userMapper.create(user);
    }
    public Double query(Integer id){
        return userMapper.query(id);
    }

    /*
     * @id user id
     * @value deposit money
     * */
    public Double deposit(Integer id, String amountStr){
        paramService.checkAmount(amountStr);
        Double balance = query(id);
        balance = balance + Double.parseDouble(amountStr);
        updateBalance(id, balance);
        return query(id);
    }

    public Double withdrawal(Integer id, String amountStr){
        paramService.checkAmount(amountStr);
        Double balance = query(id);
        Double amount = Double.parseDouble(amountStr);
        if (balance-amount<0){
            throw new CustomException("You don't have sufficient balance");
        }
        balance = balance - Double.parseDouble(amountStr);
        updateBalance(id, balance);
        System.out.println();
        return query(id);
    }

    public void updateBalance(Integer id, Double balance){
        userMapper.updateBalance(id, balance);
        return;
    }

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;
    @Value("${spring.datasource.url}")
    private String databaseUrl;

    public User SQLDirectLogin (String username, String password){
        Connection connection = null;
        Statement sqlStatement = null;
        User user = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            // Vulnerability
            /* START BAD CODE */
            String sqlQuery= "select * from USER where username ='" + username + "' AND password = '" + password + "';";
            System.out.println(sqlQuery);
            sqlStatement = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery(sqlQuery);
            /* END BAD CODE */
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getDouble("balance"));
            }
            return user;

        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
