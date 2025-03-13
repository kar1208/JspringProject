package com.spring.JspringProject.dao;

import java.util.List;

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

	@Override
	public int setUserInput(UserVo vo) {
		return sqlSession.insert("userNS.setUserInput", vo);
	}

	@Override
	public UserVo getUserSearchPart(UserVo vo) {
		System.out.println("vo(dao) : " + vo);
		return sqlSession.selectOne("userNS.getUserSearchPart", vo);
	}

	@Override
	public List<UserVo> getUserList() {
		return sqlSession.selectList("userNS.getUserList");
	}

	@Override
	public int setuserDeleteOk(int idx) {
		return sqlSession.delete("userNS.setuserDeleteOk", idx);
	}

	@Override
	public UserVo getSearchIdx(int idx) {
		return sqlSession.selectOne("userNS.getSearchIdx", idx);
	}

	@Override
	public int setUserUpdate(UserVo vo) {
		return sqlSession.update("userNS.setUserUpdate", vo);
	}

	@Override
	public List<UserVo> getUserArrayView() {
		return sqlSession.selectList("userNS.getUserArrayView");
	}

	@Override
	public List<UserVo> getUserArrayName() {
		return sqlSession.selectList("getUserArrayName");
	}

	@Override
	public List<UserVo> getUserArrayId() {
		return sqlSession.selectList("getUserArrayId");
	}
	
	
}
