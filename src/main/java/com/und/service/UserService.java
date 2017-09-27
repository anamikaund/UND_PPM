package com.und.service;

import com.und.model.User;

public interface UserService {
	User save(User user);
	boolean findByLogin(String userName, String password);
	boolean findByUserName(String userName);
}
