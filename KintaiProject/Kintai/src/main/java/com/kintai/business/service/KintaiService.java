package com.kintai.business.service;

import java.util.List;

import com.kintai.business.domain.Kintai;

public interface KintaiService {
	void save(Kintai kintai, String userId);
//	List<Kintai> findAllByTitleLike(String keyword);
}
