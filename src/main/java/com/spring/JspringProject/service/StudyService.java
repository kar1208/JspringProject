package com.spring.JspringProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.vo.ChartVo;
import com.spring.JspringProject.vo.QrCodeVo;
import com.spring.JspringProject.vo.TransactionVo;
import com.spring.JspringProject.vo.UserVo;

public interface StudyService {

	String[] getCityStringArray(String dodo);

	List<String> getCityVosArray(String dodo);

	int fileUpload(MultipartFile fName, String mid);

	int multiFileUpload(MultipartHttpServletRequest mFile);

	void getCalendar();

	String setQrCodeCreate(String mid);

	List<ChartVo> getRecentlyVisitCount(int i);

	String setQrCodeCreate(QrCodeVo vo);

	String setQrCodeCreate2(QrCodeVo vo);

	String setQrCodeCreate3(QrCodeVo vo);

	QrCodeVo setQrCodeSearch(String qrCode);

	int setTransactionUserInput(TransactionVo vo);

	int setTransactionUser1Input(UserVo vo);

	int setTransactionUser2Input(UserVo vo);

	int setTransactionUser3Input(TransactionVo vo);
	
}
