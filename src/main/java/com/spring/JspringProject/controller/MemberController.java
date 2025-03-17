package com.spring.JspringProject.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.vo.MailVo;
import com.spring.JspringProject.vo.MemberVo;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	JavaMailSender mailSender;
	
	//로그인 폼 보기
	@RequestMapping(value="/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet() {
		//쿠키처리로 저장된 아이디를 가져와서 view에 보내주기
		
		return "member/memberLogin";
	}
	
	// 회원가입 폼 보기
	@RequestMapping(value="/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
	
		return "member/memberJoin";
	}
	// 회원가입 처리하기
	@RequestMapping(value="/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost() {
		
		return "member/memberJoin";
	}
	
	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value="/memberIdCheck", method = RequestMethod.GET )
	public String memberIdCheck(String mid) {
		MemberVo vo = memberService.getMemberIdCheck(mid);
		if(vo != null) return "1";
		else return "0";
	}	
	// 이메일 인증처리
	@ResponseBody
	@RequestMapping(value="/memberEmailCheck", method = RequestMethod.POST )
	public String memberEmailCheckPost(String email) throws MessagingException {
		UUID uid = UUID.randomUUID();
		String emailKey = uid.toString().substring(0,8);
		
		mailSend(email, "이메일 인증키입니다.", "인증키 : " + emailKey);
		
		return "1";
	}	
	
	// 이메일 전송하기(인증번호, 아이디찾기, 비밀번호 찾기)
	public String mailSend(String toMail, String title, String mailFlag) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String content = "";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일보관함에 메세지 내용 저장...후...처리
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// 메세지에 추가로 필요한 사항을 messageHelper에 추가로 넣어준다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>"+mailFlag+"</h3><br>";
		content += "<p><img src=\"cid:main.png\" width='550px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>Green Project</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재된 그림파일의 경로
//		FileSystemResource file = new FileSystemResource("D:\\springProject\\springframework\\works\\JspringProject\\src\\main\\webapp\\resources\\images\\main.png");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png"));
		messageHelper.addInline("main.png", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "1";

	}
}