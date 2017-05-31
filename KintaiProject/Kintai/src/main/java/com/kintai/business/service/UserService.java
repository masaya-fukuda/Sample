package com.kintai.business.service;

import com.kintai.business.domain.User;

public interface UserService {
	User findOne(String userId);
	void save(User user);
}
