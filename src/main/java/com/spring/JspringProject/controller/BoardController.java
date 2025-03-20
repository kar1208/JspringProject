package com.spring.JspringProject.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.JspringProject.service.BoardService;
import com.spring.JspringProject.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		int totRecCnt = boardService.getBoardTotRecCnt();
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		List<BoardVo> vos = boardService.getBoardList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "board/boardList";
	}
	
	// 게시글 입력폼 보기
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	// 게시글 입력 처리
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVo vo) {
		int res = boardService.setBoardInputOk(vo) ;
		
		if(res != 0) return "board/boardInputOk";
		else return "board/boardInputNo";
		
	}
	
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, int idx, HttpSession session) {
		// 글 조회수 증가처리(재로그인 전까지 같은 게시물 조회수 증가(중복)불가)  
		@SuppressWarnings("unchecked")
		Set<Integer> viewedBoards = (Set<Integer>)
		session.getAttribute("viewedBoards");
		
		if(viewedBoards == null) {
			// 세션에 조회 목록이 없으면 새로생성
			viewedBoards = new HashSet<>();
			session.setAttribute("viewedBoards", viewedBoards);
		} // 현재 게시글이 세션에 등록되어있지 않으면 조회수 증가(contains)는 요소포함여부를 확인한다.
		if(!viewedBoards.contains(idx)) {  
			boardService.setBoardReadNumPlus(idx);
			viewedBoards.add(idx);  //세션에 현재 게시글 아이디 추가
		}
		
		BoardVo vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		
		return "board/boardContent";
	}
}
