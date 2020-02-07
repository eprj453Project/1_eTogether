package com.ssafy.edu.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.model.User;

@Repository
public class UserDaoImpl {
	String ns = "ssafy.user.";
	
	@Autowired
	private SqlSession sqlSession;

	public User login(User user) {
		return sqlSession.selectOne(ns+"login", user);
	}
	
	public int regi(User user) {
		return sqlSession.insert(ns+"regi", user);
	}
	public User findUserByEmail(String email) {
		return sqlSession.selectOne(ns+"findUserByEmail", email);
	}
	
	public User myselfDetail(String email) {
		return sqlSession.selectOne(ns+"myselfDetail", email);
	}
	
	public void updateMyself(User user) {
		sqlSession.update(ns+"updateMyself", user);
	}
	
	public void deleteMyself(User user) {
		sqlSession.delete(ns+"deleteMyself", user);
	}
	
	public List<User> findAllUsers() {
		return sqlSession.selectList(ns + "findAllUsers");
	}
	
	public int emailCheck(User user) {
		return sqlSession.selectOne(ns+"emailCheck", user);
	}
	
	public User logout(User user){
		return sqlSession.selectOne(ns+"logout", user);
	}
	
	public int pwdCheck(User user) {
		return sqlSession.selectOne(ns+"pwdCheck",user);
	}
	
	public User findEmail(User user) {
		return sqlSession.selectOne(ns+"findEmail", user);
	}
	
	public User findUserInfo(User user) {
		return sqlSession.selectOne(ns+"findUserInfo", user);
	}
	
	public void updatePwd(User user) {
		sqlSession.update(ns+"updatePwd", user);
	}

	public void findPwd(User finduserinfo) {
		sqlSession.update(ns+"updatePwd", finduserinfo);
		
	}
}