package com.spring.JspringProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.JspringProject.service.UserService;
import com.spring.JspringProject.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// user 메인화면
	@RequestMapping("/userMain")
	public String userMainGet() {
		return "user/userMain";
	}
	
	// user 등록화면 폼보기 
	@RequestMapping(value = "/userInput", method = RequestMethod.GET)
	public String userInputGet() {
		return "user/userInput";
	}
	
	// user 
	@RequestMapping(value = "/userInput", method = RequestMethod.POST)
	public String userInputPost(UserVo vo) {
	// 아이디 중복체크
			UserVo vo2 = userService.getUserIdSearch(vo.getMid());
			System.out.println("vo2 : " + vo2);
			if(vo2 != null) return "redirect:/message/userIdDuplication";
			
			// 회원 가입처리
			//System.out.println("vo ; " + vo);
	//		int res = userService.setUserInput(vo);
//			if(res != 0) return "redirect:/message/userInputOk";
//			else return "redirect:/message/userInputNo";  // redirect붙이면 controller로 간다!
			return "";
	}
}
