package com.spring.JspringProject.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "main/home";
	}
	
	// ckeditor에서의 그림파일 업로드시 수행처리되는 메소드
	@RequestMapping(value="/imageUpload")
	public void imageUploadGet(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/"); //실제경로
		String oFileName = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		byte[] bytes = upload.getBytes(); //업로드해서 넘어오는 자료는 바이너리형식으로 넘어오는데, 이걸 바이트형식으로 바꿔준다. io형식이므로 무조건 예외처리하세요. 
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName)); //서버에 올리는 것이므로 아웃풋.
		fos.write(bytes);
		
		PrintWriter out = response.getWriter();
		// 서버에서 브라우저(클라이언트)로 메세지를 전송하는 객체
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + oFileName; //실제 저장된 파일을 보여주는 매핑경로
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}"); // jason형식
		out.flush();
		
		fos.close();
	}
}
