package com.spring.JspringProject.vo;

import lombok.Data;

@Data   // 변환 안하는 것은 data 써도 됨 
public class PageVo {
	private int pag;
	private int pageSize;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	
	private String search;
	private String searchStr;
	private String searchString;
	private String part;

}
