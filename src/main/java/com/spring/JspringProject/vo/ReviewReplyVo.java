package com.spring.JspringProject.vo;

import lombok.Data;

@Data
public class ReviewReplyVo {
	
	private int replyIndx;
	private int reviewIdx;
	private String replyMid;
	private String replyNickName;
	private String replyrDate;
	private String replyContent;
}
