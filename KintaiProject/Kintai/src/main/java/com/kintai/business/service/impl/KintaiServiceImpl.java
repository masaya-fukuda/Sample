package com.kintai.business.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintai.business.domain.Kintai;
import com.kintai.business.repository.KintaiRepository;
import com.kintai.business.repository.UserRepository;
import com.kintai.business.service.KintaiService;

@Service
@Transactional
public class KintaiServiceImpl implements KintaiService{
	@Autowired
	private KintaiRepository kintaiRepository;
	
	@Autowired
	private UserRepository userRepository;
		
	@Override
	public void save(Kintai kintai,String userId) {
		kintai.setUser(userRepository.findByUserId(kintai.getUserNumber()));
		kintaiRepository.save(kintai);
	}

//	@Override
//	public List<Kintai> findAllByTitleLike(String keyword) {
//		return kintaiRepository.findAllByTitleLike("%" + keyword + "%");
//	}

}
