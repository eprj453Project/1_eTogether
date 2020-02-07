package com.ssafy.edu.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.ReviewDaoImpl;
import com.ssafy.edu.model.Review;
import com.ssafy.edu.model.ReviewResult;


@Service
public class ReviewService implements IReviewService {

	@Autowired
	private ReviewDaoImpl reviewdao;

	@Override
	public List<Review> getAllReview() {
		// TODO Auto-generated method stub
		return reviewdao.getAllReview();
	}

	@Override
	public void insertReview(ReviewResult reviewresult) {
		// TODO Auto-generated method stub
		reviewdao.insertReview(reviewresult);
	}

	@Override
	public void updateReview(ReviewResult reviewresult) {
		// TODO Auto-generated method stub
		reviewdao.updateReview(reviewresult);
	}

	@Override
	public void deleteReview(String user_email, String budget_title) {
		// TODO Auto-generated method stub
		reviewdao.deleteReview(user_email, budget_title);
	}



}
