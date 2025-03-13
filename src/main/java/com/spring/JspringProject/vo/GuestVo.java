package com.spring.JspringProject.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class GuestVo {
	private int idx;
	private String name;
	private String content;
	private String email;
	private String homePage;
	private String hostIp;
	private String visitDate;
}
