package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.JspringProject.vo.MemberVo;

public interface MemberService {

	MemberVo getMemberIdCheck(String mid);

	String fileUpload(MultipartFile fName, String mid, String photo);

	int setMemberJoinOk(MemberVo vo);

	void setMemberInforUpdate(String mid, int point);

	List<MemberVo> getMemberList(int level);

	void setMemberDeleteCheck(String mid);

	MemberVo getMemberNickCheck(String nickName);

	MemberVo getMemberIdxSearch(int idx);

	int setMemberPwdChange(String mid, String pwd);

	List<MemberVo> getMemberSearchEmail(String email);

	int setMemberUpdateOk(MemberVo vo);

	MemberVo getMemberNickNameEmailCheck(String nickName, String email);

	void setKakaoMemberInput(String mid, String nickName, String email, String pwd);

	

}
