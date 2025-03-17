package com.spring.JspringProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.MemberDao;
import com.spring.JspringProject.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;

	@Override
	public MemberVo getMemberIdCheck(String mid) {
		return memberDao.getMemberIdCheck(mid);
	}
}
