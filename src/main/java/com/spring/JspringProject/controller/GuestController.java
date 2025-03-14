package com.spring.JspringProject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.JspringProject.service.GuestService;
import com.spring.JspringProject.vo.GuestVo;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	GuestService guestService;
	
	// 방명록 전체 리스트
	@RequestMapping("/guestList")
	public String guestListGet(Model model,
		@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
		@RequestParam(name="pageSize", defaultValue="5", required = false) int pageSize
			) {
		int totRecCnt = guestService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize ) + 1;
		int startIndexNo = pageSize * (pag - 1);
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;		// 한블록의 page 개수
		int curBlock = (pag - 1) / blockSize; 			// 1번째 블록은 0으로 설정
		int lastBlock = (totPage - 1) / blockSize; 		// 마지막 블록
		
		List<GuestVo> vos = guestService.getGuestList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		
		return "guest/guestList";
	}
	
	// 방명록 글쓰기
	@RequestMapping("/guestInput")
	public String guestInputGet(Model model) {
		return "guest/guestInput";
	}
	
	
	@RequestMapping(value="/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVo vo) {
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
	
	// 관리자 로그인 폼보기
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String adminGet() {
		return "guest/admin";
	}
	// 관리자 로그인 처리
	@RequestMapping(value="/admin", method = RequestMethod.POST)
	public String adminPost(HttpSession session, String mid, String pwd) {
		if(mid.equals("admin") && pwd.equals("1234")) {
			session.setAttribute("sAdmin", "adminOk");
			return "redirect:/message/adminOk";
		}
		else {
			return "redirect:/message/adminNo";
		}
	}
	// 관리자 로그아웃 처리
	@RequestMapping(value="/adminLogout", method = RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		session.removeAttribute("sAdmin");
		return "redirect:message/adminLogout";
	}
	// 게시글 삭제 처리
	@RequestMapping(value="/guestDelete", method = RequestMethod.GET)
	public String guestDeleteGet(int idx) {
		int res = guestService.setGuestDelete(idx);
		
		if(res != 0 ) return "redirect:/message/adminDeleteOk";
		else return "redirect:/message/adminDeleteNo";
	}
}
