package com.spring.JspringProject.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UserVo {
	private int idx;
	private String mid; 
	private String pwd; 
	private String name; 
	private int age; 
	private String gender; 
	private String address;
	
	private String nickName;
	private String part;
	private String content;
	
	// user2 테이블
	private String job;
}
