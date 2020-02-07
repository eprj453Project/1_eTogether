package com.ssafy.edu.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.BudgetDaoImpl;
import com.ssafy.edu.model.Budget;
import com.ssafy.edu.model.BudgetInfo;
import com.ssafy.edu.model.BudgetList;

@Service
public class BudgetService implements IBudgetService {

	@Autowired
	private BudgetDaoImpl budgetdao;

	@Override
	public List<BudgetInfo> getMyBudgetList(String user_email) {
		// TODO Auto-generated method stub
		return budgetdao.getMyBudgetList(user_email);
	}

	@Override
	public Budget getOneBudget(String budget_num, String budget_title) {
		// TODO Auto-generated method stub
		return budgetdao.getOneBudget(budget_num, budget_title);
	}

	//내 예산안 보기, 예산안 간략한 형태로 보여주기
	@Override
	public void insertBudgetList(BudgetList budgetlist) {
		// TODO Auto-generated method stub
		budgetdao.insertBudgetList(budgetlist);
	}
	//예산안 상세보기
	@Override
	public void insertBudgetInfo(BudgetInfo budgetinfo) {
		// TODO Auto-generated method stub
		budgetdao.insertBudgetInfo(budgetinfo);
	}

	@Override
	public void deleteBudgetInfo(String user_email, String budget_title) {
		// TODO Auto-generated method stub
		budgetdao.deleteBudgetInfo(user_email,budget_title);
	}

	@Override
	public void deleteBudgetList(String user_email, String budget_title, String pro_id) {
		// TODO Auto-generated method stub
		budgetdao.deleteBudgetList(user_email,budget_title,pro_id);
	}

}
