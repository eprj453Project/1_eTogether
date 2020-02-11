package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.model.Review;
import com.ssafy.edu.model.ReviewResult;

public interface IReviewService {

	List<ReviewResult> getAllReview();

	void insertReview(ReviewResult reviewresult);

	void updateReview(ReviewResult reviewresult);

	void deleteReview(String user_email, String budget_title);

	ReviewResult getOneReview(String user_email, String budget_title);

}
