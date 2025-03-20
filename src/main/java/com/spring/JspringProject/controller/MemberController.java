package com.spring.JspringProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.vo.MemberVo;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	//로그인 폼 보기
	@RequestMapping(value="/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		//쿠키처리로 저장된 아이디를 가져와서 view에 보내주기
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		
		return "member/memberLogin";
	}
	
	//로그인 처리
	@RequestMapping(value="/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			String mid, String pwd, String idSave) {
		// 로그인인증처리(스프링 시큐리트의 BCryptPasswordEncoder객체를 이용한 암호화 되어 있는 비밀번호 비교하기)
		MemberVo vo	= memberService.getMemberIdCheck(mid);
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
			// 로그인 완료시 처리할 로직(1.세션, 2.쿠키, 3.기타 설정값(방문포인트 등) 셋팅
			
			// 1.세션
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "우수회원";
			else if(vo.getLevel() == 2) strLevel = "정회원";
			else if(vo.getLevel() == 3) strLevel = "준회원";
			
			
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			
			// 2.쿠키
			if(idSave != null && idSave.equals("on")) {
				Cookie cookieMid = new Cookie("cMid", vo.getMid());
				cookieMid.setPath("/");
				cookieMid.setMaxAge(60*60*24*7); // 단위:초...쿠키 만료시간을 7일로 지정
				response.addCookie(cookieMid);
			}
			else { //쿠키 삭제처리
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setPath("/");
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			
			// 3. 기타처리 : 방문카운트로 10포인트 증정(단, 1일 50포인트까지만 제한처리)
			int point = 10;
			
			memberService.setMemberInforUpdate(mid, point);
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else {
			return "redirect:/message/memberLoginNo";
			
		}
		
	}
	
	
	// 회원가입 폼 보기
	@RequestMapping(value="/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
	
		return "member/memberJoin";
	}
	
	// 회원가입 처리하기(DB에 회원 저장)
	@RequestMapping(value="/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVo vo, MultipartFile fName) {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 회원 사진 처리
		if(!fName.getOriginalFilename().equals("")) vo.setPhoto(memberService.fileUpload(fName, vo.getMid(), ""));
		else vo.setPhoto("noimage.jpg");
		
		// 모든 처리가 완료되면 DB에 회원 정보 저장
		int res = memberService.setMemberJoinOk(vo);
		
		if(res != 0) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
		
	}
	
	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberIdChcek", method = RequestMethod.GET)
	public String memberIdChcekGet(String mid) {
		MemberVo vo = memberService.getMemberIdCheck(mid);
		if(vo != null) return "1";
		else return "0";
	}
	
	// 닉네임 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberNickChcek", method = RequestMethod.GET)
	public String memberNickChcekGet(String nickName) {
		MemberVo vo = memberService.getMemberNickCheck(nickName);
		if(vo != null) return "1";
		else return "0";
	}

	// 이메일 인증처리
	@ResponseBody
	@RequestMapping(value="/memberEmailCheck", method = RequestMethod.POST )
	public String memberEmailCheckPost(String email, HttpSession session) throws MessagingException {
		UUID uid = UUID.randomUUID();
		String emailKey = uid.toString().substring(0,8);
		session.setAttribute("sEmailKey", emailKey);
		
		mailSend(email, "이메일 인증키입니다.", "인증키 : " + emailKey);
		
		return "1";
	}	
	
	// 이메일 인증(회원가입시) 확인하기
	@ResponseBody
	@RequestMapping(value = "/memberEmailCheckOk", method = RequestMethod.POST)
	public String memberEmailCheckOkPost(String checkKey, HttpSession session) {
		String sCheckKey = (String) session.getAttribute("sEmailKey");
		session.removeAttribute(sCheckKey);
		
		if(checkKey.equals(sCheckKey)) return "1";
		else return "0";
	}
	
	// 메일 전송하기(인증번호, 아이디찾기, 비밀번호 찾기)
	public void mailSend(String toMail, String title, String mailFlag) throws MessagingException {
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
	}
	
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet() {
		return "member/memberMain";
	}
	
	// 로그아웃 처리
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session) {
		session.invalidate();
		return "redirect:/message/memberLogoutOk";
	}
	// 회원 탈퇴(정보수정)신청을 위한 비밀번호 확인 
	@RequestMapping(value = "/pwdCheck/{pwdFlag}", method = RequestMethod.GET)
	public String pwdCheckGet(Model model , @PathVariable String pwdFlag) {
		model.addAttribute("pwdFlag", pwdFlag);
		return "member/pwdCheckForm";
	}
	
//비밀번호 확인처리후 지정된 위치로 보내기처리
	@RequestMapping(value = "/pwdCheck/{pwdFlag}", method = RequestMethod.POST)
	public String pwdCheckPost(HttpSession session, @PathVariable String pwdFlag, String pwd) {
		String mid = (String) session.getAttribute("sMid");
		//MemberVo vo = memberService.getMemberIdCheck(mid);
		// 비밀번호 확인후 비밀번호가 잘못되었을경우의 메세지 처리('d/p/u')
		if(!passwordEncoder.matches(pwd, memberService.getMemberIdCheck(mid).getPwd())) {
			if(pwdFlag.equals("d")) return "redirect:/message/pwdCheckNo"; 
			else if(pwdFlag.equals("p")) return "redirect:/message/pwdCheckNoP"; 
			else if(pwdFlag.equals("u")) return "redirect:/message/pwdCheckNoU"; 

		}
		
		// 비밀번호가 맞으면 해당 항목 처리할수 있도록 보내준다.
		if(pwdFlag.equals("d")) {
			memberService.setMemberDeleteCheck(mid);
			return "redirect:/message/memberDeleteCheck";
		}
		else if(pwdFlag.equals("p")) return "member/memberPassCheckForm";
		else if(pwdFlag.equals("u")) return "redirect:/member/memberUpdate";
		
		return "${ctp}/";
	}
	
	// 전체 회원 보기
	@GetMapping("/memberList")
	public String memberListGet(Model model,
		@RequestParam(name="level", defaultValue = "99", required = false )	int level) {
		List<MemberVo> vos =  memberService.getMemberList(level);
		model.addAttribute("vos", vos);
		
		return "member/memberList";
	}
	// 비밀번호 변경처리
	@PostMapping("/pwdChange")
	public String pwdChangeGet(HttpSession session, String pwd) {
		String mid = (String) session.getAttribute("sMid");
		pwd = passwordEncoder.encode(pwd);
		int res = memberService.setMemberPwdChange(mid, pwd);
		
		if(res != 0) return "redirect:/message/pwdChangeOk";
		else return "redirect:/message/pwdChangeNo";
	}
	
	// 아이디 찾기폼 보기
	@RequestMapping(value="/midSearchForm", method = RequestMethod.GET)
	public String midSearchFormGet() {
	
		return "member/midSearchForm";
	}
	// 아이디 찾기
	@PostMapping("/midSearchForm")
	public String midSearchFormPost(Model model , @RequestParam("email") String email) {
		List<MemberVo> vos = memberService.getMemberSearchEmail(email); 
		
		if(vos.isEmpty()) return "해당 이메일로 등록된 아이디가 없습니다";
		else {
			model.addAttribute("vos", vos);
			return "member/midSearch";
		}
	
	}
	
	// 회원 정보 수정폼 보기
	@RequestMapping(value="/memberUpdate" , method = RequestMethod.GET)
	public String memberUpdateGet(Model model, HttpSession session ) {
		String mid = (String) session.getAttribute("sMid");
		MemberVo vo = memberService.getMemberIdCheck(mid);
		model.addAttribute("vo",vo);
		
		return "/member/memberUpdate";
	}
	
	// 회원 정보 수정처리
	@RequestMapping(value="/memberUpdate" , method = RequestMethod.POST)
	public String memberUpdatePost(HttpSession session, MemberVo vo, MultipartFile fName) {
		//닉네임 체크(수정시에는 새로 세션에 저장처리한다.)
		String nickName = (String) session.getAttribute("sNickName");
		if (memberService.getMemberNickCheck(vo.getNickName()) !=null && !nickName.equals(vo.getNickName())) {
			return "redirect:/message/nickCheckNo";
		}
		// 회원 사진 처리
		if(fName.getOriginalFilename() != null && !fName.getOriginalFilename().equals("")) {
			vo.setPhoto(memberService.fileUpload(fName, vo.getMid(), vo.getPhoto()));
		}
		
		int res = memberService.setMemberUpdateOk(vo);
		
		if(res != 0) {
			session.setAttribute("sNickName", vo.getNickName());
			return "redirect:/message/memberUpdateOk";
		}
		else return "redirect:/message/memberUpdateNo";
	}
	
}
