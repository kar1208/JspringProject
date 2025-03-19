package com.spring.JspringProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.JspringProject.service.AdminService;
import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.vo.MemberVo;

@Controller
@RequestMapping("/admin")
public class adminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminLeft")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@GetMapping("/adminContent")
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
	@GetMapping("/member/memberList")
	public String memberListGet(Model model,
		@RequestParam(name="level", defaultValue = "99", required = false )	int level) {
		List<MemberVo> vos =  memberService.getMemberList(level);
		model.addAttribute("vos", vos);
		model.addAttribute("level", level);
		
		return "admin/member/memberList";
	}
	
	// 선택된 회원 레벨 등급 변경처리 . ajax는 포스트든 겟이든 다 넘어온다.(안적어도)
	@ResponseBody 
	@RequestMapping(value = "/memberLevelChange", method = RequestMethod.POST)
	public String memberLevelChangePost(int level, int idx) {
		//int res = adminService.setMemberLevelChange(level, idx);
		System.out.println("level: "+ level + ", idx : " + idx);
		return adminService.setMemberLevelChange(level, idx) + "";
	}

	// 개별 회원 정보 상세보기
	@RequestMapping(value = "/memberInfor/{idx}", method = RequestMethod.GET)
	public String memberInforGet(Model model,@PathVariable int idx) {
		//pathvariable방식으로 넘어옴
		MemberVo vo = memberService.getMemberIdxSearch(idx);
		model.addAttribute("vo", vo);
		return "admin/member/memberInfor";
	}
	
}
