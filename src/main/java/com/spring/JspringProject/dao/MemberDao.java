package com.spring.JspringProject.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.MemberVo;

public interface MemberDao {

	MemberVo getMemberIdCheck(@Param("mid") String mid);

}
