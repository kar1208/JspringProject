package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.GuestDao;
import com.spring.JspringProject.vo.GuestVo;

@Service
public class GuestServiceImpl implements GuestService {
	
	@Autowired
	GuestDao guestDao;

	@Override
	public List<GuestVo> getGuestList() {
		return guestDao.getGuestList();
	}

	@Override
	public int getGuestInput(GuestVo vo) {
		return guestDao.getGuestInput(vo);
	}

	@Override
	public List<GuestVo> guestArrayView() {
		return guestDao.guestArrayView();
	}

	@Override
	public List<GuestVo> guestArrayName() {
		return guestDao.guestArrayName();
	}
}
