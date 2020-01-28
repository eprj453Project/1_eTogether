package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dao.UserDaoImpl;
import com.ssafy.edu.model.BoolResult;
import com.ssafy.edu.model.NumberResult;
import com.ssafy.edu.model.RandomPassword;
import com.ssafy.edu.model.User;
import com.ssafy.edu.model.UserseqResult;
import com.ssafy.edu.service.IUserService;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//http://localhost:8197/together/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
@Api(value = "SSAFY", description = "SSAFY Resouces Management 2019")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;

	@ApiOperation(value = "회원가입 ", response = UserseqResult.class)
	@RequestMapping(value = "/regi", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> regi(@RequestBody User dto) throws Exception {
		logger.info("3-------------regi-----------------------------" + dto);
		
	//	int total = userService.regi(dto);
		NumberResult nr = new NumberResult();
	//	nr.setCount(total);
		nr.setName("regi");
		nr.setState("succ");
	//	logger.info("3-------------regi-----------------------------" + total);
		
//		if (total <= 0) {
//			nr.setCount(-1);
//			nr.setName("regi");
//			nr.setState("fail");
//
//			return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
//		}
		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	/////////////////////////////////////////////////////////////////////////
	@ApiOperation(value = "이메일 중복 체크 ", response = UserseqResult.class)
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> emailCheck(@PathVariable String email) throws Exception {
		logger.info("3-------------emailCheck-----------------------------" + email);
		
		int total = userService.emailCheck(email);
		NumberResult nr = new NumberResult();
		nr.setCount(total);
		nr.setName("emailCheck");
		nr.setState("succ");
		logger.info("3-------------emailCheck-----------------------------" + total);
		
		if (total <= 0) {
			nr.setCount(-1);
			nr.setName("emailCheck");
			nr.setState("fail");
			// return new ResponseEntity(HttpStatus.NO_CONTENT);
			return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "로그인 ", response = UserseqResult.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> login(@RequestBody User dto) throws Exception {
		logger.info("3-------------login-----------------------------" + dto);
		
		User user = userService.login(dto);
		
		UserseqResult nr = new UserseqResult();
		nr.setCount(user.getCount());
		nr.setName("login");
		nr.setLoginEmail(user.getEmail());
		nr.setState("succ");
		nr.setIsLogin(true);
		
		logger.info("3-------------login-----------------------------" + user.getCount());
		
		if (user.getCount() <= 0) {
			nr.setCount(-1);
			nr.setName("login");
			nr.setState("fail");
			nr.setLoginEmail(user.getEmail());
			nr.setIsLogin(false);
			
			// return new ResponseEntity(HttpStatus.NO_CONTENT);
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	// 로그아웃
	@ApiOperation(value = "로그아웃 ", response = UserseqResult.class)
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> logout(@RequestBody User dto) throws Exception {
		logger.info("3-------------logout-----------------------------" + dto);
		
		// int total = memService.logout(dto);
		User user = userService.logout(dto);
		
		UserseqResult nr = new UserseqResult();
		nr.setCount(user.getCount());
		nr.setName("logout");
		nr.setLoginEmail("");
		nr.setState("succ");
		nr.setIsLogin(false);
		
		logger.info("3-------------logout-----------------------------" + user.getCount());
		
		if (user.getCount() <= 0) {
			nr.setCount(-1);
			nr.setName("logout");
			nr.setState("fail");
			nr.setLoginEmail(user.getEmail());
			nr.setIsLogin(true);
			
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "내 정보 확인할 때 비밀번호 체크", response = UserseqResult.class)
	@RequestMapping(value = "/pwdCheck", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> pwdCheck(@RequestBody User dto) throws Exception {
		logger.info("3-------------pwdCheck-----------------------------" + dto);
		
		int total = userService.pwdCheck(dto);
		NumberResult nr = new NumberResult();
		nr.setCount(total);
		nr.setName("pwdCheck");
		nr.setState("succ");
		logger.info("3-------------pwdCheck-----------------------------" + total);
		
		if (total <= 0) {
			nr.setCount(-1);
			nr.setName("pwdCheck");
			nr.setState("fail");
			return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	// 세션에 있는 아이디
	@ApiOperation(value = "내 정보 보기", response = User.class)
	@RequestMapping(value = "/myselfDetail/{email}", method = RequestMethod.POST)
	public ResponseEntity<User> myselfDetail(@PathVariable String email) throws Exception {
		logger.info("2-------------myselfDetail-----------------------------" + new Date());
		
		User userdetail = userService.myselfDetail(email);

		if (userdetail.equals("") || userdetail == null || email.equals("")) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} 
		

		System.out.println("memdetail : "+ userdetail);
		return new ResponseEntity<User>(userdetail, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일 찾기", response = User.class)
	@RequestMapping(value = "/findEmail", method = RequestMethod.POST)
	public ResponseEntity<User> findEmail(@RequestBody User dto) throws Exception {
		logger.info("2-------------findEmail dto-----------------------------" + dto);
		
		User finduser = userService.findEmail(dto);

		if (finduser.getEmail().equals("") || finduser.getEmail() == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(finduser, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "pwd 찾기", response = User.class)
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	public ResponseEntity<User> findPwd(@RequestBody User dto) throws Exception {
		logger.info("2-------------findPwd-----------------------------" + new Date());
		logger.info("2-------------findPwd dto-----------------------------" + dto);
	
		User finduserinfo = userService.findUserInfo(dto);
		
		if(finduserinfo.getEmail() != null && finduserinfo.getName() != null) {
			String temp_pwd = RandomPassword.getRandomPassword();
			System.out.println("temp_pwd : "+ temp_pwd);
			
			finduserinfo.setPwd(temp_pwd);
			finduserinfo.setTemp_pwd(temp_pwd);
			
			System.out.println("임시 비밀번호 " + finduserinfo.getTemp_pwd());
			
			userService.findPwd(finduserinfo); //임시 비밀번호 로 변경
			logger.info("2-------------finduserinfo-----------------------------" + finduserinfo);
			
			
			return new ResponseEntity<User>(finduserinfo, HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

	}
	
	

	// 세션에 있는 아이디
	@ApiOperation(value = "내 정보 수정", response = BoolResult.class)
	@RequestMapping(value = "/updateMyself", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> updateMyself(@RequestBody User dto) throws Exception {
		logger.info("5-------------updateMyself-----------------------------" + new Date());
		logger.info("5-------------updateMyself-----------------------------" + dto);

		userService.updateMyself(dto);
		User ans = userService.findUserByEmail(dto.getEmail());
		UserseqResult nr = new UserseqResult();
		nr.setName("updateMyself");
		nr.setState("succ");
		logger.info("5-------------updateMyself-------id------------------" + nr);
		if (ans == null || ans.getEmail() == null) {
			nr.setSeq(0);
			nr.setName("updateMyself");
			nr.setState("fail");
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	// 세션에 있는 아이디
	@ApiOperation(value = "pwd 수정", response = BoolResult.class)
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> updatePwd(@RequestBody User dto) throws Exception {
		logger.info("5-------------updatePwd-----------------------------" + new Date());
		logger.info("5-------------updatePwd-----------------------------" + dto);
		
		userService.updatePwd(dto);
		User ans = userService.findUserByEmail(dto.getEmail());
		UserseqResult nr = new UserseqResult();
		
		nr.setName("updatePwd");
		nr.setState("succ");
		logger.info("5-------------updatePwd------------------------" + nr);
		
		if (ans == null || ans.getEmail() == null) {
			nr.setSeq(0);
			nr.setName("updatePwd");
			nr.setState("fail");
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "탈퇴", response = BoolResult.class)
	@RequestMapping(value = "/deleteMyself", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> deleteMyself(@PathVariable String email) throws Exception {
		logger.info("6-------------deleteMyself-----------------------------" + new Date());
		logger.info("6-------------deleteMyself-----------------------------" + email);
		
		userService.deleteMyself(email);
		User ans = userService.findUserByEmail(email);
		UserseqResult nr = new UserseqResult();
		
		nr.setName("deleteMyself");
		nr.setState("succ");
		
		logger.info("6-------------deleteMyself------------------------" + nr);
		
		if (ans == null) {
			nr.setSeq(0);
			nr.setName("deleteMyself");
			nr.setState("fail");
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	// 관리자	
	int recentPage = 0;
	
	@ApiOperation(value = "회원정보 리스트를 반환한다.", response = List.class)
	@RequestMapping(value = "/findAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAllUsers(@RequestParam(required=false, defaultValue="0") String nowPage) throws Exception {
		logger.info("1-------------findAllUsers-----------------------------" + new Date());

//		int totPage = (int) Math.ceil(userService.cntTotMember() / 5.0);
//		System.out.println("totPage : " + totPage);
//		if (nowPage == "" || nowPage == null) {
//			recentPage = 1;
//		} else {
//			recentPage = Integer.parseInt(nowPage);
//		}
//		System.out.println("recentPage :" + recentPage);
//		int startContent = (recentPage - 1) * 5;
//		System.out.println("startContent : " + startContent);
//
//		List<Member> memlist = memService.findAllMembers(startContent);	
//		memlist.get(0).setTotPage(totPage);
		
		List<User> userList = userService.findAllUsers();
		System.out.println(userList);
	

		if (userList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			System.out.println(userList.get(0));
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일로 회원 검색하기", response = User.class)
	@RequestMapping(value = "/findUserByEmail/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> findUserByEmail(@PathVariable String email) throws Exception {
		logger.info("1-------------findUserByEmail-----------------------------" + new Date());
		User isUser = userService.findUserByEmail(email);

		if (isUser == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(isUser, HttpStatus.OK);
	}
}