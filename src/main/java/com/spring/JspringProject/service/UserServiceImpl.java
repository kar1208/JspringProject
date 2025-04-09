package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.UserDao;
import com.spring.JspringProject.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao UserDao;

	@Override
	public UserVo getUserIdSearch(String mid) {
		return UserDao.getUserIdSearch(mid);
	}

	@Override
	public int setUserInput(UserVo vo) {
		return UserDao.setUserInput(vo);
	}

	@Override
	public UserVo getUserSearchPart(UserVo vo) {
		System.out.println("vo(service) : " + vo);
		return UserDao.getUserSearchPart(vo);
	}

	@Override
	public List<UserVo> getUserList() {
		return UserDao.getUserList();
	}

	@Override
	public int setuserDeleteOk(int idx) {
		return UserDao.setuserDeleteOk(idx);
	}

	@Override
	public UserVo getSearchIdx(int idx) {
		return UserDao.getSearchIdx(idx);
	}

	@Override
	public int setUserUpdate(UserVo vo) {
		return UserDao.setUserUpdate(vo);
	}

	@Override
	public List<UserVo> getUserArrayView() {
		return UserDao.getUserArrayView();
	}

	@Override
	public List<UserVo> getUserArrayName() {
		return UserDao.getUserArrayName();
	}

	@Override
	public List<UserVo> getUserArrayId() {
		return UserDao.getUserArrayId();
	}

	@Override
	public List<UserVo> getUser2List() {
		return UserDao.getUser2List();
	}
	
}
