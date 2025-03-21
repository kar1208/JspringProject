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
		System.out.println("pageSize : " + pageSize);
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
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		
		int res = boardService.setBoardInputOk(vo) ;
		
		if(res != 0) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
		
	}
	
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, int idx, int pag, int pageSize, HttpSession session) {
		// 글 조회수 증가처리(재로그인 전까지 같은 게시물 조회수 증가(중복)불가)  
		@SuppressWarnings("unchecked")
		Set<Integer> viewedBoards = (Set<Integer>)session.getAttribute("viewedBoards");
		
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
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		return "board/boardContent";
	}
	// 게시글 삭제 처리 
	@RequestMapping(value="/boardDelete" , method = RequestMethod.GET)
	public String boardDeleteGet(int idx) {
		// 게시글에 사진이 존재한다면 실제 파일을 board폴더에서 삭제시킨다.
		BoardVo vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 사진작업 완료후 DB에 저장된 실제 정보레코드를 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo?idx="+idx;
	}
	
	
	// 게시글 수정 폼 보기
	@RequestMapping(value="/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model, int idx) {
		// 수정처리시, 수정폼을 호출할 때 현재게시글에 그림이 존재한다면 그림파일 모두를 ckeditor폴더로 복사시켜둔다.
		BoardVo vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		model.addAttribute("vo", vo);
		
		
		return "board/boardUpdate";
	}
	
	// 게시글 수정 처리
	@RequestMapping(value="/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVo vo) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다.
		BoardVo dbvo = boardService.getBoardContent(vo.getIdx());
		
		// content의 내용이 조금이라도 변경이 되었다면 내용을 수정처리한것이기에, 그림파일 처리유무를 결정한다.
		if(!dbvo.getContent().equals(vo.getContent())) {
			// 1.기존 board폴더의 그림이 존재했다면, 원본그림을 모두 삭제처리한다.
			if(dbvo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(dbvo.getContent());
			
			// 2.삭제후 'board'폴더를 'ckeditor'폴더로 경로 변경
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 1,2번 작업을 마치면 처음 글을 올릴때와 똑같은 상황으로 처리하면 된다.
			if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
			
			// 이미지 복사작업을 모두 마치면(ckeditor폴더에서 board폴더로) 'ckeditor -> board'로 다시 변경한다.
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		int res = boardService.setBoardUpdate(vo);
		
		
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo?idx="+vo.getIdx();
	}
	
	
}
