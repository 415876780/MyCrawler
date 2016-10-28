package com.computer.service;

import org.junit.Test;  
import org.springframework.beans.factory.annotation.Autowired;  

import com.github.pagehelper.PageInfo;
import com.computer.baseTest.SpringTestCase;

import ccnu.computer.model.User;
import ccnu.computer.service.UserService;

public class UserServiceTest extends SpringTestCase {
	
	@Autowired  
    private UserService userService; 
	
//	@Test  
//    public void selectUserByIdTest(){  
//        User user = userService.selectUserById(1);  
//        System.out.println(user.getUserName() + ":" + user.getUserPassword());
//    }  
	
	@Test  
    public void queryByPageTest(){  
        PageInfo<User> page =  userService.queryByPage(null, 1, 1);
        System.out.println(page);
    }
}
