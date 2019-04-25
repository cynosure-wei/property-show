package com.jessienwei.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jessienwei.web.dto.RoleDTO;
import com.jessienwei.web.dto.User;
import com.jessienwei.web.repository.RoleRepository;
import com.jessienwei.web.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        RoleDTO role = roleRepository.findByType("USER");
        user.setRole(role);
        //System.err.print(role.toString());
		userRepository.save(user);
	}

}