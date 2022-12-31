package com.cathay.coindesk.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathay.coindesk.client.BpiClient;
import com.cathay.coindesk.client.bean.CurrentpriceBpiOut;
import com.cathay.coindesk.client.bean.CurrentpriceOut;
import com.cathay.coindesk.controller.bean.AddIn;
import com.cathay.coindesk.controller.bean.UpdIn;
import com.cathay.coindesk.dao.BpiDao;
import com.cathay.coindesk.ex.BpiException;
import com.cathay.coindesk.ex.BpiIllegalArgumentException;
import com.cathay.coindesk.model.Bpi;
import com.cathay.coindesk.util.BeanUtil;
import com.cathay.coindesk.util.DateUtil;


@Service
public class BpiService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BpiDao dao;
	
	@Autowired
	private BpiClient client;
	
	public CurrentpriceOut client(){
		return client.get();
	}
	
	public List<Bpi> query(){
		return dao.findAll();
	}
	
	@Transactional
	public Bpi add(AddIn in) {
		if(in == null)
			throw new BpiIllegalArgumentException("please enter.");
		if(existCode(in.getCode()))
			throw new BpiException("code always exist.");
		
		Bpi o = new Bpi();
		BeanUtils.copyProperties(in, o);
		o.setUpd_datetime(DateUtil.getLocalDateTime());
		return dao.save(o);
	}
	
	@Transactional
	public Bpi upd(UpdIn in) {
		if(in == null)
			throw new BpiIllegalArgumentException("please enter.");
		Bpi o = null;
		try {
			o = dao.getReferenceById(in.getId());
		}catch (EntityNotFoundException e) {
			throw new BpiException("code not exist.");
		}
		BeanUtils.copyProperties(in, o, BeanUtil.getNullPropertyNames(in));
		o.setUpd_datetime(DateUtil.getLocalDateTime());
		return dao.save(o);
	}
	
	public void del(Long id) {
		dao.deleteById(id);
	}
	
	private boolean existCode(String code) {
		Bpi o = new Bpi();
		o.setCode(code);
		return dao.exists(Example.of(o));
	}
	
	@Transactional
	public void sync() {
		Map<String, CurrentpriceBpiOut> map = client.getCurrentprice();
		if(map == null)
			return;
		for(String k : map.keySet()) {
			CurrentpriceBpiOut v = map.get(k);
			if(v == null)
				return;
			List<Bpi> list = dao.findByCode(v.getCode());
			if(list == null || list.size() == 0) {
				Bpi o = new Bpi();
				BeanUtils.copyProperties(v, o);
				o.setUpd_datetime(DateUtil.getLocalDateTime());
				dao.save(o);
			}else {
				Bpi o = list.get(0);
				BeanUtils.copyProperties(v, o);
				o.setUpd_datetime(DateUtil.getLocalDateTime());
				dao.save(o);
			}
		}
		
	}
}
