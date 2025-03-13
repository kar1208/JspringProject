package com.spring.JspringProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.JspringProject.service.GuestService;
import com.spring.JspringProject.vo.GuestVo;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	GuestService guestService;
	
	// 방명록 전체 리스트
	@RequestMapping("/guestList")
	public String guestListGet(Model model) {
		List<GuestVo> vos = guestService.getGuestList();
		model.addAttribute("vos", vos);
		
		return "guest/guestList";
	}
	
	// 방명록 글쓰기
	@RequestMapping("/guestInput")
	public String guestInputGet(Model model) {
		return "guest/guestInput";
	}
	
	
	@RequestMapping(value="/guestInput", method = RequestMethod.POST)
	public String guestInputPost(Model model, GuestVo vo) {
		int res = guestService.getGuestInput(vo);
		
		if(res != 0 ) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}
	
	// 방명록 순서 정렬(빠른등록순)
	@RequestMapping("/guestArrayView")
	public String guestArrayViewGet(Model model) {
		List<GuestVo> vos = guestService.guestArrayView();
		model.addAttribute("vos", vos);
		
		return "guest/guestList";
	}
//방명록 순서 정렬(이름순)
	@RequestMapping("/guestArrayName")
	public String guestArrayNameGet(Model model) {
		List<GuestVo> vos = guestService.guestArrayName();
		model.addAttribute("vos", vos);
		
		return "guest/guestList";
	}
}
