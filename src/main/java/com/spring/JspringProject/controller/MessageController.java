package com.spring.JspringProject.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model, @PathVariable String msgFlag, HttpSession session, 
			HttpServletRequest request, 
				@RequestParam(name="mid", defaultValue = "", required = false) String mid,
				@RequestParam(name="idx", defaultValue = "0", required = false) int idx,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
				@RequestParam(name="search", defaultValue = "", required = false) String search,
				@RequestParam(name="searchString", defaultValue = "", required = false) String searchString,
				@RequestParam(name="tempFlag", defaultValue = "", required = false) String tempFlag
				
			) {
		
		if(msgFlag.equals("userInputOk")) {
			model.addAttribute("message", "회원에 가입되었습니다.");
			model.addAttribute("url", "user/userMain");
		}
		
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("message", "회원에 가입실패");
			model.addAttribute("url", "user/userInput");
		}
		
		// user2
		if(msgFlag.equals("user2InputOk")) {
			model.addAttribute("message", "회원에 가입되었습니다.");
			model.addAttribute("url", "user2/userMain");
		}
		
		else if(msgFlag.equals("user2InputNo")) {
			model.addAttribute("message", "회원에 가입실패");
			model.addAttribute("url", "user2/userInput");
		}
		
		
		
		else if(msgFlag.equals("homeOk")) {
			model.addAttribute("message", "홈으로 갑니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("userIdDuplication")) {
			model.addAttribute("message", "이미 사용중인 아이디 입니다. \\n다른 아이디로 가입하세요.");
			model.addAttribute("url", "/");
		}
		
		
		else if(msgFlag.equals("userSearchNo")) {
			model.addAttribute("message", "검색한 회원이 없습니다.");
			model.addAttribute("url", "user/userSearch");
		}
		
		//
		else if(msgFlag.equals("user2SearchNo")) {
			model.addAttribute("message", "검색한 회원이 없습니다.");
			model.addAttribute("url", "user2/userSearch");
		}
		
		
		else if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("message", "회원을 삭제처리 하였습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("message", "회원 삭제실패");
			model.addAttribute("url", "user/userList");
		}
		
		
		//
		else if(msgFlag.equals("user2DeleteOk")) {
			model.addAttribute("message", "회원을 삭제처리 하였습니다.");
			model.addAttribute("url", "user2/userList");
		}
		else if(msgFlag.equals("user2DeleteNo")) {
			model.addAttribute("message", "회원 삭제실패");
			model.addAttribute("url", "user2/userList");
		}
		
		
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원정보 수정완료");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("message", "회원정보 수정실패");
			model.addAttribute("url", "user/userList");
		}
		
		//
		else if(msgFlag.equals("user2UpdateOk")) {
			model.addAttribute("message", "회원정보 수정완료");
			model.addAttribute("url", "user2/userList");
		}
		else if(msgFlag.equals("user2UpdateNo")) {
			model.addAttribute("message", "회원정보 수정실패");
			model.addAttribute("url", "user2/userList");
		}
		
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록 등록완료");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("message", "방명록 등록실패");
			model.addAttribute("url", "guset/guestInput");
		}
		
		
		else if(msgFlag.equals("adminOk")) {
			model.addAttribute("message", "관리자 로그인 성공!!");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "관리자 로그인 실패~~");
			model.addAttribute("url", "guest/admin");
		}
		else if(msgFlag.equals("adminLogout")) {
			model.addAttribute("message", "관리자 로그아웃");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminDeleteOk")) {
			model.addAttribute("message", "관리자가 게시글을 삭제했습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminDeleteNo")) {
			model.addAttribute("message", "관리자로 게시글 삭제 실패~~");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("message", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("message", "파일 업로드 실패");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("message", "메일을 성공적으로 발송했습니다.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n확인하시고 다시 가입하세요");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("message", "닉네임이 중복되었습니다.\\n확인하시고 다시 가입하세요");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("message", "회원 가입되셨습니다.\\n로그인후 사용하세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원 가입실패.\\n다시 회원가입 해주세요");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("message", mid + "회원님 로그인 되셨습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("message", "로그인 실패~ 다시 로그인해 주세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		
		else if(msgFlag.equals("memberLogoutOk")) {
			model.addAttribute("message", "로그아웃 되었습니다.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdCheckNo")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheckForm/d");
		}
		else if(msgFlag.equals("pwdCheckNop")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheckForm/p");
		}
		else if(msgFlag.equals("pwdCheckNop")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheckForm/u");
		}
		else if(msgFlag.equals("memberDeleteCheck"))	 {
			model.addAttribute("message", "탈퇴처리되었습니다.");
			session.invalidate();
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdChangeOk"))	 {
			model.addAttribute("message", "회원 비밀번호를 변경하였습니다.\\n다시 로그인해 주세요.");
			session.invalidate();
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdChangeNo"))	 {
			model.addAttribute("message", "회원 비밀번호를 변경 실패~~~");
			model.addAttribute("url", "member/pwdCheck/p");
		}
		else if(msgFlag.equals("nickCheckNo"))	 {
			model.addAttribute("message", "이미 사용중인 닉네임입니다.");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("memberUpdateOk"))	 {
			model.addAttribute("message", "회원 정보가 수정되었습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberUpdateNo"))	 {
			model.addAttribute("message", "회원 정보 수정실패~~");
			model.addAttribute("url", "member/memberUpdate");
		}
		
		
		
		else if(msgFlag.equals("boardInputOk"))	 {
			model.addAttribute("message", "게시글 등록되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardInputNo"))	 {
			model.addAttribute("message", "게시글 등록실패");
			model.addAttribute("url", "board/boardInput");
		}
		
		else if(msgFlag.equals("boardDeleteOk"))	 {
			model.addAttribute("message", "게시글 삭제 하였습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo"))	 {
			model.addAttribute("message", "게시글 삭제 실패~~");
			model.addAttribute("url", "board/boardContent?idx="+idx);
		}
		else if(msgFlag.equals("boardUpdateOk"))	 {
			model.addAttribute("message", "게시글 수정 성공");
			model.addAttribute("url", "board/boardContent?pag="+pag+"&pageSize="+pageSize+"&search="+search+"&searchString="+searchString+"&idx="+idx);
		}
		else if(msgFlag.equals("boardUpdateNo"))	 {
			model.addAttribute("message", "게시글 수정 실패");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize+"&search="+search+"&searchString="+searchString+"&idx="+idx);
		}
		
		
		else if(msgFlag.equals("multiFileUploadOk"))	 {
			model.addAttribute("message", "멀티파일 업로드 성공");
			model.addAttribute("url", "study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("multiFileUploadNo"))	 {
			model.addAttribute("message", "멀티파일 업로드 실패");
			model.addAttribute("url", "study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("pdsDeleteOk"))	 {
			model.addAttribute("message", "첨부파일이 삭제되었습니다.");
			model.addAttribute("url", "pds/pdsList");
		}
		else if(msgFlag.equals("pdsDeleteNo"))	 {
			model.addAttribute("message", "첨부파일 삭제 실패");
			model.addAttribute("url", "pds/pdsList");
		}
		else if(msgFlag.equals("wmMemberIdNo"))	 {
			model.addAttribute("message", "가입된 회원정보가 없습니다.\\n다시 입력하세요.");
			model.addAttribute("url", "webMessage/webMessage?mSw=0");
		}
		else if(msgFlag.equals("wmInputOk"))	 {
			model.addAttribute("message", "메세지가 전송되었습니다.");
			model.addAttribute("url", "webMessage/webMessage?mSw=3");
		}
		else if(msgFlag.equals("wmInputNo"))	 {
			model.addAttribute("message", "메세지 전송 실패.");
			model.addAttribute("url", "webMessage/webMessage?mSw=0");
		}
		else if(msgFlag.equals("transactionUserInputOk"))	 {
			model.addAttribute("message", "user 회원 저장 완료");
			model.addAttribute("url", "study/validator/validatorForm");
		}
		else if(msgFlag.equals("transactionUserInputNo"))	 {
			model.addAttribute("message", "user 회원 저장 실패");
			model.addAttribute("url", "study/validator/validatorForm");
		}
		else if(msgFlag.equals("backEndCheckNo")) {
//		String str = "";
//		if(tempFlag.equals("midSizeNo")) str = "아이디 길이를 확인하세요";
//		else if(tempFlag.equals("nameEmpty")) str = "성명을 확인하세요";
//		else if(tempFlag.equals("ageRangeNo")) str = "나이 범위를 확인하세요";
//		model.addAttribute("message", "저장실패("+str+" 를 확인해 보세요)-BackEndCheck");
		model.addAttribute("message", "저장실패("+tempFlag+")-BackEndCheck");
		model.addAttribute("url", "study/validator/validatorForm");
		}
		else if(msgFlag.equals("transactionUserInputOk")) {
			model.addAttribute("message", "user테이블에 회원이 등록되었습니다.");
			model.addAttribute("url", "study/transaction/transactionForm");
		}


		
		return "include/message";
	}
}