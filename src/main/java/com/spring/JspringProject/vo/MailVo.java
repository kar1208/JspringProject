package com.spring.JspringProject.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MailVo {
	private String toMail;
	private String title;
	private String content;
}
