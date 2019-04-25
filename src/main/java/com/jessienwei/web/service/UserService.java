package com.jessienwei.web.service;

import com.jessienwei.web.dto.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
