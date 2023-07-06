package com.hientran.sohebox.service;

import com.hientran.sohebox.SoheboxApplicationTests;
import com.hientran.sohebox.authentication.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceTest extends SoheboxApplicationTests {

	final UserDetailsServiceImpl userService;

//    @Test
	public void getByIdTest() {
		try {
			// userService.getById((long) 1);
		} catch (Exception e) {
//            Assert.fail();
		}
	}

}
