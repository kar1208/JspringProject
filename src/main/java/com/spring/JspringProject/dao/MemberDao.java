package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.MemberVo;

public interface MemberDao {

	MemberVo getMemberIdCheck(@Param("mid") String mid);

	int setMemberJoinOk(@Param("vo") MemberVo vo);

	void setMemberInforUpdate(@Param("mid") String mid, @Param("point")  int point);

	List<MemberVo> getMemberList(@Param("level") int level);

	void setMemberDeleteCheck(@Param("mid") String mid);

	MemberVo getMemberNickCheck(@Param("nickName") String nickName);




}
