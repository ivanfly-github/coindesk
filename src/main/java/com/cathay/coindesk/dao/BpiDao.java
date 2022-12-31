package com.cathay.coindesk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cathay.coindesk.model.Bpi;

@Repository
public interface BpiDao extends JpaRepository<Bpi, Long> {

	List<Bpi> findByCode(String code);
}
