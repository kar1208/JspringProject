package com.spring.JspringProject.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.JspringProject.vo.UserVo;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public UserVo getUserIdSearch(String mid) {
		return sqlSession.selectOne("userNS.getUserIdSearch", mid);
	}
	
	
}
