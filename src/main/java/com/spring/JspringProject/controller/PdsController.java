package com.spring.JspringProject.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.common.Pagination;
import com.spring.JspringProject.service.PdsService;
import com.spring.JspringProject.vo.PageVo;
import com.spring.JspringProject.vo.PdsVo;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	Pagination pagination;
	
	@Autowired
	PdsService pdsService;
	
	// 자료실 파일 목록보기
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		PageVo pageVo = pagination.getTotRecCnt(pag, pageSize, "pds", part, "");	
		
		List<PdsVo> vos = pdsService.getPdsList(pageVo.getStartIndexNo(), pageVo.getPageSize(),part,"","");
		
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("vos", vos);
		
		return "pds/pdsList";
	}
	
	// 자료실 자료(파일)입력폼 보기
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		model.addAttribute("part",part);
		return "pds/pdsInput";
	}
	
	// 자료실 자료(파일) 입력 처리
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(MultipartHttpServletRequest mFName , PdsVo vo) {
		int res = pdsService.setPdsInput(mFName, vo);
				
		if(res != 0) return "redirect:/message/pdsInputOk";
		else return "redirect:/message/pdsInputNo";
	}
	
	// 자료실 자료(파일) 삭제 처리
	@RequestMapping(value = "/pdsDelete", method = RequestMethod.POST)
	public String pdsDeletePost(int idx, MultipartHttpServletRequest fSName, PdsVo vo) {
		
		int res = pdsService.setPdsDelete(idx,fSName, vo);
		
		
		if(res != 0) return "redirect:/message/pdsDeleteOk";
		else return "redirect:/message/pdsDeleteNo";
	}
}
