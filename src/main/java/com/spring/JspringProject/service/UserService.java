package com.spring.JspringProject.service;

import java.util.List;

import com.spring.JspringProject.vo.UserVo;

public interface UserService {

	UserVo getUserIdSearch(String mid);

	int setUserInput(UserVo vo);

	UserVo getUserSearchPart(UserVo vo);

	List<UserVo> getUserList();

	int setuserDeleteOk(int idx);

	UserVo getSearchIdx(int idx);

	int setUserUpdate(UserVo vo);

	List<UserVo> getUserArrayView();

	List<UserVo> getUserArrayName();

	List<UserVo> getUserArrayId();

	List<UserVo> getUser2List();

}
