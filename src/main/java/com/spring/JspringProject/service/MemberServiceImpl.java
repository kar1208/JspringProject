package com.spring.JspringProject.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.JspringProject.common.ProjectProvide;
import com.spring.JspringProject.dao.MemberDao;
import com.spring.JspringProject.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;

	@Autowired
	ProjectProvide projectProvide;
	
	@Override
	public MemberVo getMemberIdCheck(String mid) {
		return memberDao.getMemberIdCheck(mid);
	}

	@Override
	public String fileUpload(MultipartFile fName, String mid, String photo) {
		// 파일이름 중복방지를 위한 처리
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid + "_" + UUID.randomUUID().toString().substring(0,8) + "_" + oFileName;
		
		try {
			ProjectProvide.writeFile(fName, sFileName, "member");  // 서버에 파일 올리기
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sFileName;
	}

	@Override
	public int setMemberJoinOk(MemberVo vo) {
		return memberDao.setMemberJoinOk(vo);
	}

	@Override
	public void setMemberInforUpdate(String mid, int point) {
		memberDao.setMemberInforUpdate(mid, point);
	}

	@Override
	public List<MemberVo> getMemberList(int level) {
		return memberDao.getMemberList(level);
	}

	@Override
	public void setMemberDeleteCheck(String mid) {
		memberDao.setMemberDeleteCheck(mid);
	}

	@Override
	public MemberVo getMemberNickCheck(String nickName) {
		return memberDao.getMemberNickCheck(nickName);
	}

}
