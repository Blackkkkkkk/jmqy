package com.dhxx.service.mapper.user;

import java.util.List;

import com.dhxx.facade.entity.user.User;

public interface UserMapper {

	List<User> find(User user);
	
	Long update(User user);
	
	void save(User user);
	
	void delete(User user);

	String wechatfind(String s);

}
