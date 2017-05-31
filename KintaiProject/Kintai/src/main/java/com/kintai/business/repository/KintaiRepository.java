package com.kintai.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kintai.business.domain.Kintai;

@Repository
public interface KintaiRepository extends JpaRepository<Kintai, Long> {
	List<Kintai> findByDate(String date);
//	List<Kintai> findAllByTitleLike(String keyword);
}
