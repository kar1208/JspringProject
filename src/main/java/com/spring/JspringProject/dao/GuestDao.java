package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.GuestVo;

public interface GuestDao {

	List<GuestVo> getGuestList();

	int getGuestInput(@Param("vo") GuestVo vo);

	List<GuestVo> guestArrayView();

	List<GuestVo> guestArrayName();

}
