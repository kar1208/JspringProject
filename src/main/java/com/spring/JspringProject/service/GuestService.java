package com.spring.JspringProject.service;

import java.util.List;

import com.spring.JspringProject.vo.GuestVo;

public interface GuestService {

	List<GuestVo> getGuestList();

	int getGuestInput(GuestVo vo);

	List<GuestVo> guestArrayView();

	List<GuestVo> guestArrayName();

}
