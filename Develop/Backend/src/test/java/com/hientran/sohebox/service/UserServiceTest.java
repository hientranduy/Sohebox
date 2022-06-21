package com.hientran.sohebox.service;

//import org.junit.Assert;
//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hientran.sohebox.SoheboxApplicationTests;
import com.hientran.sohebox.security.UserService;

/**
 * User service junit test
 *
 * @author hientran
 */
public class UserServiceTest extends SoheboxApplicationTests {

    @Autowired
    UserService userService;

//    @Test
    public void getByIdTest() {
        try {
            // userService.getById((long) 1);
        } catch (Exception e) {
//            Assert.fail();
        }
    }

}
