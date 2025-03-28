package com.spring.JspringProject.service;

import java.util.List;

import com.spring.JspringProject.vo.ReviewVo;

public interface ReviewService {

	int setReviewInputOk(ReviewVo vo);

	List<ReviewVo> getPdsReview(String part, int idx);

	double getPdsReviewAge(String part, int idx);
	
}
