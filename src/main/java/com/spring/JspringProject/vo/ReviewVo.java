package com.spring.JspringProject.vo;

import lombok.Data;

@Data
public class ReviewVo {
	
	private int idx;
	private String part;
	private String partIdx;
	private String mid;
	private String nickName;
	private String star;
	private String content;
	private String rDate;
	
	private int replyIndx;
	private int reviewIdx;
	private String replyMid;
	private String replyNickName;
	private String replyrDate;
	private String replyContent;
}
