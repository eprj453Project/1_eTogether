package com.ssafy.edu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.model.Review;
import com.ssafy.edu.model.ReviewResult;


@Repository
public class ReviewDaoImpl {
	String ns = "ssafy.review.";

	@Autowired
	private SqlSession sqlSession;

	public List<Review> getAllReview() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(ns+"getAllReview");
	}

	public void insertReview(ReviewResult reviewresult) {
		// TODO Auto-generated method stub
		sqlSession.insert(ns+"insertReview",reviewresult);
	}

	public void updateReview(ReviewResult reviewresult) {
		// TODO Auto-generated method stub
		sqlSession.update(ns+"updateReview",reviewresult);		
	}

	public void deleteReview(String user_email, String budget_title) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_email", user_email);
		paramMap.put("budget_title", budget_title);
		sqlSession.delete(ns+"deleteReview",paramMap);
	}
	
	

}
