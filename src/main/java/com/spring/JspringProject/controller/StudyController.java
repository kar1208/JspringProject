package com.spring.JspringProject.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.common.ARIAUtil;
import com.spring.JspringProject.common.SecurityUtil;
import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.service.StudyService;
import com.spring.JspringProject.service.UserService;
import com.spring.JspringProject.vo.ChartVo;
import com.spring.JspringProject.vo.CrawlingVo;
import com.spring.JspringProject.vo.DbPayMentVo;
import com.spring.JspringProject.vo.MailVo;
import com.spring.JspringProject.vo.MemberVo;
import com.spring.JspringProject.vo.QrCodeVo;
import com.spring.JspringProject.vo.TransactionVo;
import com.spring.JspringProject.vo.UserVo;

import io.github.bonigarcia.wdm.WebDriverManager;

//@RestController
@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwoEncoder;
	
	@RequestMapping("/ajax/ajaxForm")
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
//	동기식 호출
//	@RequestMapping("/ajax/ajaxTest1")
//	public String ajaxTest1Get(Model model ,int idx) {
//		model.addAttribute(idx);
//		return "study/ajax/ajaxForm";
//	}
	
	@ResponseBody  //이 메소드를 호출할때만 RestController의 영향을 받는다.	
	@RequestMapping(value="/ajax/ajaxTest1", produces="application/text; charset=utf-8")
//	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.POST) 이렇게만 하면 한글 깨짐. 위처럼 써야함.
	public String ajaxTest1Get(int idx) {
		String str = "전송값: " + idx;
		return str;
	}
	
	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.GET) 
	public String ajaxTest2_1Get() {
		return "study/ajax/ajaxTest2_1";
	}
	
//	@ResponseBody
//	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.POST) 
//	public String[] ajaxTest2_1Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);
//		return strArray;
//	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.POST) 
	public String[] ajaxTest2_1Post(String dodo) {
		return studyService.getCityStringArray(dodo);
	}
	
	
	@RequestMapping(value="/ajax/ajaxTest2_2", method = RequestMethod.GET) 
	public String ajaxTest2_2Get() {
		return "study/ajax/ajaxTest2_1";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2_2", method = RequestMethod.POST) 
	public List<String> ajaxTest2_2Post(String dodo) {
		return studyService.getCityVosArray(dodo);
	}
	
	//싱글파일 업로드 폼보기
	@RequestMapping(value="/fileUpload/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload");
		
		String[] files = new File(realPath).list();
		
		for(String file : files ) {
			System.out.println("file : " + file);
		}
		
		model.addAttribute("files", files);
		
		return "study/fileUpload/fileUpload";
	}
	//싱글파일 업로드처리 (1개 > MultipartFile )
	@RequestMapping(value="/fileUpload/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		int res = studyService.fileUpload(fName, mid);
		
		if (res != 0) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	//선택된 파일 1개 삭제 처리
	@ResponseBody
	@RequestMapping(value="/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File fName = new File(realPath + file);
		
		if(fName.exists()) {
			fName.delete();
			res = "1";
		}
		return res;
	}
	//모든 파일 1개 삭제 처리
	@ResponseBody
	@RequestMapping(value="/fileUpload/fileDeleteAll", method = RequestMethod.POST)
	public String fileDeleteAllPost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File folder = new File(realPath);
		if(!folder.exists()) return res;
		
		File[] files = folder.listFiles();
		
		if(files.length != 0) {
			for(File file : files) {
				file.delete();
			}
			res = "1";
		}
		return res;
	}
	
	
	//멀티파일 업로드 폼보기 (1개 > MultipartFile )
	@RequestMapping(value="/fileUpload/multiFile", method = RequestMethod.GET)
	public String multiFileGet() {
		return "study/fileUpload/multiFile";
	}
	
	//멀티파일 업로드처리 (1개 > MultipartFile )
	@RequestMapping(value="/fileUpload/multiFile", method = RequestMethod.POST)
	public String multiFilePost(MultipartHttpServletRequest mFile ) {
		int res = studyService.multiFileUpload(mFile);
		
			if (res != 0) return "redirect:/message/multiFileUploadOk";
			else return "redirect:/message/multiFileUploadNo";
	}
	
	
	//메일 연습폼 보기
	@RequestMapping(value="/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet(HttpServletRequest request) {
		
		return "study/mail/mailForm";
	}	
	//메일 연습 보내기
	@RequestMapping(value="/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(HttpServletRequest request, MailVo vo) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일보관함에 메세지 내용 저장...후...처리
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// 메세지에 추가로 필요한 사항을 messageHelper에 추가로 넣어준다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>JspringProject 에서 보냅니다.</h3><br>";
		content += "<p><img src=\"cid:main.png\" width='550px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>Green Project</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재된 그림파일의 경로
//		FileSystemResource file = new FileSystemResource("D:\\springProject\\springframework\\works\\JspringProject\\src\\main\\webapp\\resources\\images\\main.png");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png"));
		messageHelper.addInline("main.png", file);
		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg"));
		messageHelper.addAttachment("chicago.jpg", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.zip"));
		messageHelper.addAttachment("chicago.zip", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}	
	
	// 모달연습
	@RequestMapping(value = "modal/modalForm", method = RequestMethod.GET)
	public String modalFormGet(Model model) {
		model.addAttribute("name", "홍길동");
		model.addAttribute("age", "22");
		model.addAttribute("address", "서울");
		
		//관리자의 정보를 front에 따로 출력하시오.
		MemberVo vo = memberService.getMemberIdCheck("admin");
		model.addAttribute("vo", vo);
		
		List<MemberVo> vos = memberService.getMemberList(99);  //99는 전체자료 조회
		model.addAttribute("vos", vos);
		
		return "study/modal/modalForm";
	}
	
	// 인터넷 달력 연습
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String calendarGet() {
		studyService.getCalendar();
		
		return "study/calendar/calendar";
	}
	
	// 크롤링(jsoup) 폼 보기
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.GET)
	public String jsoupGet() {
		
		return "study/crawling/jsoup";
	}
	
	// 크롤링(jsoup) 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String jsoupPost(String url, String selector) throws IOException {
		
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		String str = "";
		for(Element element : elements) {
			i++;
		//	System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			str += i + ". " + element.text() + "<br/>";
		}
		
		return str;
	}
	
	// 크롤링(jsoup)2 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup2", method = RequestMethod.POST)
	public List<String> jsoup2Post(String url, String selector) throws IOException {
		
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			//	System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			vos.add(i + ". " + element.text());
		}
		
		return vos;
	}
	// 크롤링(jsoup)3 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup3", method = RequestMethod.POST)
	public List<String> jsoup3Post(String url, String selector) throws IOException {
		
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			//	System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			vos.add(i + ". " + element.html().replace("data-", ""));
		}
		
		return vos;
	}
	// 크롤링(jsoup)4 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup4", method = RequestMethod.POST)
	public List<String> jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements elements = document.select(searchSelector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
				System.out.println("element : " + element);
		//	System.out.println(i + ". " + element.text());
//			vos.add(i + ". " + element.html().replace("data-", ""));
			vos.add(i + ". " + element.html());
		}
		
		return vos;
	}
	

	// 크롤링(jsoup)5 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup5", method = RequestMethod.POST)
	public List<String> jsoup5Post(String search, String searchSelector) throws IOException {
		
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements elements = document.select(searchSelector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			//	System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			vos.add(i + ". " + element.html().replace("data-lazy", ""));
		}
		
		return vos;
	}
	
	
	// 크롤링(jsoup)6 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup6", method = RequestMethod.POST)
	public List<CrawlingVo> jsoup6Post(String url, String selector) throws IOException {
		
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = null;
		String[] selectors = selector.split("/");
		
		elements = document.select(selectors[0]);
		ArrayList<String> titleVos = new ArrayList<String>();
		for(Element element : elements) {
			titleVos.add(element.html());
		}
		
		elements = document.select(selectors[1]);
		ArrayList<String> imageVos = new ArrayList<String>();
		for(Element element : elements) {
			imageVos.add(element.html());
		}
		
		elements = document.select(selectors[2]);
		ArrayList<String> boardcastVos = new ArrayList<String>();
		for(Element element : elements) {
			boardcastVos.add(element.html());
		}
		
		List<CrawlingVo> vos = new ArrayList<CrawlingVo>();
		CrawlingVo vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVo();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(boardcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 크롤링(selecnium) 폼 보기
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
	public String seleniumGet() {
		
		return "study/crawling/selenium";
	}
	// 크롤링(selecnium) 처리하기
	@ResponseBody
	@RequestMapping(value = "/crawling/selenium1", method = RequestMethod.POST)
	public String selenium1Post(HttpServletRequest request, String url, String searchString) throws IOException {
		// 외부 드라이버를 다운받아서 '/resources/crawling'폴더에 넣어두었다면?
//		String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
//		System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
// driver 버전이 너무 낮을경우 △ 		
		
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebDriverManager.chromedriver().setup();
		
		driver.get(url);
		
		WebElement btnSearch = driver.findElement(By.name("q")); // 커서가 들어간것
		btnSearch.sendKeys(searchString);
		btnSearch.sendKeys(Keys.ENTER);
		
//		driver.close();
		
		return "";
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/crawling/selenium2", method = RequestMethod.POST)
	public List<Map<String, Object>> selenium2Post(HttpServletRequest request, String url, String searchString) throws IOException {
		List<Map<String, Object>> vos = new ArrayList<Map<String,Object>>();
		
		WebDriver driver = new ChromeDriver();
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebDriverManager.chromedriver().setup();
		
		driver.get("http://www.cgv.co.kr/movies/?lt=1&ft=0"); 
		
		WebElement btnMore = driver.findElement(By.id("chk_nowshow")); // 커서가 들어간것
		btnMore.click();
		
		try {	Thread.sleep(1000); } catch (Exception e) {}//1초 (1/1000초)
		
		btnMore = driver.findElement(By.className("link-more")); // 커서가 들어간것
		btnMore.click();
		
		try {	Thread.sleep(2000); } catch (Exception e) {}
		
		//int i = 0;
		List<WebElement> elements = driver.findElements(By.cssSelector("div.sect-movie-chart ol li"));
		for(WebElement element : elements) {
			Map<String, Object> map = new HashMap<String, Object>();
			//i++;
			String link = element.findElement(By.tagName("a")).getAttribute("href");
			String title = "<a href='"+link+"' target'_blank'>" + element.findElement(By.className("title")).getText() + "</a>";
			String image = "<img src='"+ element.findElement(By.tagName("img")).getAttribute("src") +"' width='200px' />"; 
			String percent = element.findElement(By.className("percent")).getText();
			//String percent = element.findElement(By.xpath("//*[@id=\"contents\"]/div[1]/div[3]/ol[1]/li["+i+"]/div[2]/div/div/span[2]")).getText();
			
			System.out.println("title : " + title);
			System.out.println("image : " + image);
			System.out.println("percent : " + percent);
			
			map.put("title", title);
			map.put("image", image);
			map.put("percent", percent);
			
			vos.add(map);
		}
		//DB저장처리
			driver.close();
		
		return vos;
	}
	
	
	
	// 크롤링연습 처리(selenium) - SRT 열차 조회하기
	@ResponseBody
	@RequestMapping(value = "/crawling/train", method = RequestMethod.POST)
	public List<HashMap<String, Object>> trainPost(HttpServletRequest request, String stationStart, String stationStop) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String,Object>>();
		try {
			WebDriver driver = new ChromeDriver();
			
      WebDriverManager.chromedriver().setup();			
			
			driver.get("http://srtplay.com/train/schedule");

			WebElement btnMore = driver.findElement(By.xpath("//*[@id=\"station-start\"]/span"));
			btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-pos-input\"]"));
      btnMore.sendKeys(stationStart);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-arrive\"]/span"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      btnMore = driver.findElement(By.id("station-pos-input"));
      
      btnMore.sendKeys(stationStop);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}

      btnMore = driver.findElement(By.xpath("//*[@id=\"sr-train-schedule-btn\"]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      List<WebElement> timeElements = driver.findElements(By.cssSelector(".table-body ul.time-list li"));
 			
      HashMap<String, Object> map = null;
      
			for(WebElement element : timeElements){
				map = new HashMap<String, Object>();
				String train=element.findElement(By.className("train")).getText();
				String start=element.findElement(By.className("start")).getText();
				String arrive=element.findElement(By.className("arrive")).getText();
				String time=element.findElement(By.className("time")).getText();
				String price=element.findElement(By.className("price")).getText();
				map.put("train", train);
				map.put("start", start);
				map.put("arrive", arrive);
				map.put("time", time);
				map.put("price", price);
				array.add(map);
			}
			
      // 요금조회하기 버튼을 클릭한다.(처리 안됨 - 스크린샷으로 대체)
      btnMore = driver.findElement(By.xpath("//*[@id=\"scheduleDiv\"]/div[2]/div/ul/li[1]/div/div[5]/button"));
      //System.out.println("요금 조회버튼클릭");
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	// 웹(구글) 차트 연습
	@GetMapping("/chart/chart1")
	public String chart1Get(Model model,
			@RequestParam(name="part", defaultValue = "barV", required=false) String part
			) {
		model.addAttribute("part",part);
		return "/study/chart/chart1";
	}
	// 웹(구글) 차트 연습2
	@GetMapping("/chart2/chart2")
	public String chart2Get(Model model,
			@RequestParam(name="part", defaultValue = "barV", required=false) String part
			) {
		model.addAttribute("part",part);
		return "/study/chart2/chart2";
	}
	
	@RequestMapping(value = "/chart2/googleChart2", method = RequestMethod.POST)
	public String googleChart2Post(Model model, ChartVo vo) {
		System.out.println("vo: " + vo);
		model.addAttribute("vo", vo);
		return "study/chart2/chart2";
	
	}
	

	@RequestMapping(value="/chart2/goggleChart2Recently", method = RequestMethod.POST) 
		public String googleChart2RecentlyGet(Model model, ChartVo vo) {
			//System.out.println("part : " + vo.getPart());
			
			List<ChartVo> vos = null;
			if(vo.getPart().equals("lineChartVisitCount")) {
				vos = studyService.getRecentlyVisitCount(1);
				// vos자료를 차트에 표시처리가 잘 되지 않을경우에는 각각의 자료를 다시 편집해서 차트로 보내줘야 한다.
				String[] visitDates = new String[7];
				int[] visitCounts = new int[7];
				
				for(int i=0; i<7; i++) {
					visitDates[i] = vos.get(i).getVisitDate();
					visitCounts[i] = vos.get(i).getVisitCount();
				}
				
				model.addAttribute("part", vo.getPart());
				model.addAttribute("xTitle", "방문날짜");
				model.addAttribute("regend", "하루 총 방문자수");
				
				model.addAttribute("visitDates", visitDates);
				model.addAttribute("visitCounts", visitCounts);
				model.addAttribute("title", "최근 7일간 방문횟수");
				model.addAttribute("subTitle", "(최근 7일간 방문한 해당일자의 방문자 총수를 표시합니다.");
			}
			return "study/chart2/chart2Form";
	}

		// 임의의 영문자와 숫자를 랜덤하게 생성시켜주기
	@RequestMapping(value="/alphaNumericForm", method = RequestMethod.GET)
	public String alphaNumericFormGet() {
		
		return "/study/alphaNumeric/alphaNumericForm";
	}
	
	// 임의의 영문자와 숫자를 랜덤하게 생성시켜주기
	@ResponseBody
	@RequestMapping(value="/randomAlphaNumeric", method = RequestMethod.POST)
	public String randomAlphaNumericPost() {
//		String res = RandomStringUtils.randomAlphanumeric(32);
		return RandomStringUtils.randomAlphanumeric(64);
	}
	
	
	// QR Code 폼보기
	@RequestMapping(value="/qrCode/qrCodeForm", method = RequestMethod.GET)
	public String qrCodeFormGet() {
		return "study/qrCode/qrCodeForm";
	}
	// QR Code 만들기
	@ResponseBody
	@RequestMapping(value="/qrCode/qrCodeCreate", method = RequestMethod.POST)
	public String qrCodeCreatePost(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("sMid");		
		
		return studyService.setQrCodeCreate(mid);
	}
	
	// QR Code 개인정보 등록폼보기
	@RequestMapping(value="/qrCode/qrCodeEx1", method = RequestMethod.GET)
	public String qrCodeEx1Get() {
		return "study/qrCode/qrCodeEx1";
	}
	
	// QR Code 만들기(개인정보를 QR코드로 생성하기)
	@ResponseBody
	@RequestMapping(value="/qrCode/qrCoedeCreate1", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String qrCoedeCreate1Post(QrCodeVo vo) {
		
		return studyService.setQrCodeCreate(vo);
	}
	
	
	// 소개사이트 등록폼보기
	@RequestMapping(value="/qrCode/qrCodeEx2", method = RequestMethod.GET)
	public String qrCodeEx2Get() {
		return "study/qrCode/qrCodeEx2";
	}
	
	// 소개사이트 만들기(개인정보를 QR코드로 생성하기)
	@ResponseBody
	@RequestMapping(value="/qrCode/qrCodeCreate2", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String qrCodeCreate2Post(QrCodeVo vo) {
		
		return studyService.setQrCodeCreate2(vo);
	}
	
	
	// 티켓예매 등록폼보기
	@RequestMapping(value="/qrCode/qrCodeEx3", method = RequestMethod.GET)
	public String qrCodeEx3Get() {
		return "study/qrCode/qrCodeEx3";
	}
	
	// 티켓예매 만들기(티켓예매 QR코드로 생성 및 티켓정보 DB에 전송하기)
	@ResponseBody
	@RequestMapping(value="/qrCode/qrCodeCreate3", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String qrCodeCreate3Post(QrCodeVo vo) {
		
		return studyService.setQrCodeCreate3(vo);
	}
	
	// QR Code내용 DB에서 검색하기
	@ResponseBody
	@RequestMapping(value="/qrCode/qrCodeSearch", method = RequestMethod.POST)
	public QrCodeVo qrCodeSearchPost(String qrCode) {
		
		return studyService.setQrCodeSearch(qrCode);
	}
	
	
	
	
	// 암호화(password) 체크 폼 연습
	@GetMapping("/password/passwordForm")
	public String passwordFormGet() {
		return "study/password/passwordForm";
	}
	
	// 암호화(sha256) 체크 연습
	@ResponseBody 
	@PostMapping(value="/password/sha256Check", produces="application/text; charset=utf-8")
	public String sha256CheckPost(String pwd) {
		String salt = UUID.randomUUID().toString().substring(0,8);
		
		
		SecurityUtil securityUtil = new SecurityUtil();
		String encPwd = securityUtil.encryptSHA256(salt+pwd);
		
		return encPwd;
	}
	
	
	
	// 암호화(bCryptPasswordCheck) 체크 연습
	@ResponseBody 
	@PostMapping(value="/password/bCryptPasswordCheck", produces="application/text; charset=utf-8")
	public String bCryptPasswordCheckPost(String pwd) {
		return passwoEncoder.encode(pwd);
	}
	
	// 암호화(aria) 체크 연습
	@ResponseBody 
	@PostMapping(value="/password/ariaCheck", produces="application/text; charset=utf-8")
	public String ariaCheckPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String salt = UUID.randomUUID().toString().substring(0,8);
		String encPwd = ARIAUtil.ariaEncrypt(salt + pwd); 
		String decPwd = ARIAUtil.ariaDecrypt(encPwd); 
		
		return "암호화:\n" + encPwd + ",복호화:" + decPwd.substring(decPwd.indexOf(":")+9);
	}
	
	
	
	
	
	
	// BackEnd 체크(Validator 처리) 폼 보기
	@RequestMapping(value="/validator/validatorForm", method = RequestMethod.GET)
	public String validatorFormGet(Model model) {
		List<UserVo> vos = userService.getUserList();
		model.addAttribute("vos", vos);
		
		return "study/validator/validatorForm";
	}
	
	// user테이블에 user아이디 중복체크
	@ResponseBody
	@RequestMapping(value="/validator/userIdCheck", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String validatorPost(String mid) {
		UserVo vo = userService.getUserIdSearch(mid);
		System.out.println("vo : " + vo);
		if(vo == null) return "사용 가능한 아이디 입니다.";
		return "이미 사용중인 아이디 입니다.";
	}
	
	// BackEnd 체크(Validator 처리)
	@RequestMapping(value="/validator/validatorForm", method = RequestMethod.POST)
	public String validatorFormPost(@Validated TransactionVo vo, BindingResult bindingResult) throws IOException {
		/*
		 * if(vo.getMid().length() < 2 || vo.getMid().length() > 20) { PrintWriter out =
		 * response.getWriter(); out.println("<script>");
		 * out.println("alert('아이디는 2~20자 까지 사용하실수 있습니다.');"); out.println("</script>");
		 * }
		 */
		if(bindingResult.hasFieldErrors()) {
			System.out.println("error발생");
			System.out.println("에러 내역 : " + bindingResult);
			List<ObjectError> listError = bindingResult.getAllErrors();
			String[] temp = null;
			for(ObjectError list : listError) {
				temp = list.getDefaultMessage().split("/");
				System.out.println("메세지 : " + temp[0] + " : " + temp[1]);
				break;
			}
			return "redirect:/message/backEndCheckNo?tempFlag="+temp[1];
		}
		
		int res = studyService.setTransactionUserInput(vo);
		
		if(res != 0) return "redirect:/message/transactionUserInputOk";
		return "redirect:/message/transactionUserInputNo";
	}
	
	
	
	
	// Transaction(트랜잭션) 연습 폼 보기
	@RequestMapping(value="/transaction/transactionForm", method = RequestMethod.GET)
	public String transactionFormGet(Model model) {
		List<UserVo> vos = userService.getUserList();
		model.addAttribute("vos",vos);
		
		List<UserVo> vos2 = userService.getUser2List();
		model.addAttribute("vos2",vos2);
		
		return "study/transaction/transactionForm";
	}
	// Transaction(트랜잭션) 연습 user/user2 테이블에 등록내역 저장처리
	@Transactional
	@RequestMapping(value="/transaction/transactionForm", method = RequestMethod.POST)
	public String transactionFormPost(UserVo vo) {
		int res = 0;
		
		res = studyService.setTransactionUser1Input(vo); //user 테이블 등록
		res = studyService.setTransactionUser2Input(vo); //user2 테이블 등록
		
		
		if(res != 0) return "redirect:/message/transactionUserInputOk";
		else return "redirect:/message/transactionUserInputNo";
	}
	
	
	
	// Transaction(트랜잭션) 연습 user/user2 테이블에 등록내역 저장처리
	@Transactional
	@RequestMapping(value="/transaction/transactionUser3Input", method = RequestMethod.POST)
	public String transactionUser3InputPost(TransactionVo vo) {
		int res = 0;
		
		res = studyService.setTransactionUser3Input(vo); //'user/user2' 테이블 등록
		
		
		if(res != 0) return "redirect:/message/transactionUserInputOk";
		else return "redirect:/message/transactionUserInputNo";
	}
	
	
	
	// 결제처리 연습하기 폼 보기
	@RequestMapping(value="/payment/paymentForm", method = RequestMethod.GET)
	public String paymentFormGet(String mid) {
		return "study/payment/paymentForm";
	}
	
	// 결제처리 연습하기 처리
	@RequestMapping(value="/payment/paymentForm", method = RequestMethod.POST)
	public String paymentFormPost(DbPayMentVo vo, HttpSession session, Model model) {
		session.setAttribute("sPaymentVo", vo);
		model.addAttribute("vo", vo);
		return "study/payment/payment";
	}

	// 결제처리 연습(결제 처리 완료후 수행하는 부분)
	@RequestMapping(value="/payment/paymentOk", method = RequestMethod.GET)
	public String paymentOkGet(HttpSession session, Model model) {
		DbPayMentVo vo = (DbPayMentVo) session.getAttribute("sPaymentVo");
		model.addAttribute("vo", vo);
		session.removeAttribute("sPaymentVo");
		return "study/payment/paymentOk";
	}
	
	
}
