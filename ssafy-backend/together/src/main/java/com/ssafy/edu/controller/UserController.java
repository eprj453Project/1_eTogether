package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.edu.dao.UserDaoImpl;
import com.ssafy.edu.model.BoolResult;
import com.ssafy.edu.model.MailUtil;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Api(value = "SSAFY", description = "SSAFY 2020")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;
	
	@Autowired
	PasswordEncoder encoder;


	@ApiOperation(value = "회원가입을 한다. ", response = UserseqResult.class)
	@RequestMapping(value = "/regi", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> regi(@RequestBody User dto) throws Exception {
		logger.info("3-------------regi-----------------------------" + dto);

		int total = userService.regi(dto);
		NumberResult nr = new NumberResult();
		nr.setCount(total);
		nr.setName("regi");
		nr.setState("succ");
		if (total <= 0) {
			nr.setCount(-1);
			nr.setName("regi");
			nr.setState("fail");
			return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일 중복 체크를 한다. ", response = UserseqResult.class)
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> emailCheck(@RequestBody User dto) throws Exception {
		logger.info("3-------------emailCheck-----------------------------" + dto);
		int total = userService.emailCheck(dto);
		NumberResult nr = new NumberResult();
		nr.setCount(total);
		nr.setName("emailCheck");
		nr.setState("succ");
		if (total <= 0) {
			nr.setCount(-1);
			nr.setName("emailCheck");
			nr.setState("fail");
			return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "로그인을 한다. ", response = UserseqResult.class)
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
		if (user.getCount() <= 0) {
			nr.setCount(-1);
			nr.setName("login");
			nr.setState("fail");
			nr.setLoginEmail(user.getEmail());
			nr.setIsLogin(false);
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "로그아웃을 한다. ", response = UserseqResult.class)
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> logout(@RequestBody User dto) throws Exception {
		logger.info("3-------------logout-----------------------------" + dto);
		User user = userService.logout(dto);
		UserseqResult nr = new UserseqResult();
		nr.setCount(user.getCount());
		nr.setName("logout");
		nr.setLoginEmail("");
		nr.setState("succ");
		nr.setIsLogin(false);
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

	@ApiOperation(value = "내 정보 확인할 때 비밀번호를 체크한다.", response = UserseqResult.class)
	@RequestMapping(value = "/pwdCheck", method = RequestMethod.POST)
	public ResponseEntity<NumberResult> pwdCheck(@RequestBody User dto) throws Exception {

		logger.info("3-------------pwdCheck-----------------------------" + dto);
		System.out.println("----------------------------------------" + dto);
		int total = userService.pwdCheck(dto);

		logger.info("Start pwdCheck ");

		NumberResult nr = new NumberResult();

		try {
			// db에 있는지 확인
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPwd()));

			nr.setCount(userService.pwdCheck(dto));
			nr.setName("pwdCheck");
			nr.setState("succ");
			
			logger.info("pwdCheck Result : Succ");
			
		} catch (Exception e) {
			nr.setCount(-1);
			nr.setName("pwdCheck");
			nr.setState("fail");

			logger.info("pwdCheck Result : Fail");
		}

		return new ResponseEntity<NumberResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "현재 로그인 되어있는 이메일의 상세 정보를 볼 수 있다.", response = User.class)
	@RequestMapping(value = "/myselfDetail/{email}", method = RequestMethod.POST)
	public ResponseEntity<User> myselfDetail(@PathVariable String email) throws Exception {
		logger.info("2-------------myselfDetail-----------------------------" + new Date());
		User userdetail = userService.myselfDetail(email);
		if (userdetail.equals("") || userdetail == null || email.equals("")) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		System.out.println("memdetail : " + userdetail);
		return new ResponseEntity<User>(userdetail, HttpStatus.OK);
	}
	
	@ApiOperation(value = "이메일을 잊었을 때 이메일을 찾을 수 있다.", response = User.class)
	@RequestMapping(value = "/findEmail", method = RequestMethod.POST)
	public ResponseEntity<User> findEmail(@RequestBody User dto) throws Exception {
		logger.info("2-------------findEmail dto-----------------------------" + dto);
		User finduser = userService.findEmail(dto);
		if (finduser.getEmail().equals("") || finduser.getEmail() == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(finduser, HttpStatus.OK);
	}

	
	@ApiOperation(value = "비밀번호를 잊었을 때 비밀번호를 찾기 위한 임시 비밀번호를 발급한다.", response = User.class)
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	public ResponseEntity<User> findPwd(@RequestBody User dto) throws Exception {
		logger.info("2-------------findPwd dto-----------------------------" + dto);
		User finduserinfo = userService.findUserInfo(dto);
		if (finduserinfo.getEmail() != null && finduserinfo.getName() != null) {
			
			String temp_pwd = RandomPassword.getRandomPassword();
			System.out.println("temp_pwd : " + temp_pwd);
			String encode_temp_pwd=encoder.encode(temp_pwd);
			finduserinfo.setPwd(encode_temp_pwd);
			finduserinfo.setTemp_pwd(encode_temp_pwd);
//			System.out.println("임시 비밀번호 " + temp_pwd);
			
			userService.findPwd(finduserinfo); // 임시 비밀번호 로 변경
//			System.out.println("db에 임시비밀번호로 바뀜");
			
			String subject = "[E-Together] 임시 비밀번호 발급 안내";
			String msg = "";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana>";
			msg += "<h3 style='color:blue;'><strong>"+dto.getName();
			msg += "님</strong>의 임시 비밀번호 입니다. 로그인 후 비밀번호를 변경하세요.</h3>";
			msg += "<p>임시 비밀번호 : <strong>" + temp_pwd + "</strong></p></div>";
			MailUtil.sendMail(dto.getEmail(), subject, msg);
			
			logger.info("2-------------finduserinfo-----------------------------" + finduserinfo);
			return new ResponseEntity<User>(finduserinfo, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
   
	@ApiOperation(value = "현재 로그인되어있는 이메일의 정보를 수정한다.", response = BoolResult.class)
	@RequestMapping(value = "/updateMyself", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> updateMyself(@RequestBody User dto) throws Exception {
		logger.info("5-------------updateMyself-----------------------------" + dto);
		userService.updateMyself(dto);
		User ans = userService.findUserByEmail(dto.getEmail());
		UserseqResult nr = new UserseqResult();
		nr.setName("updateMyself");
		nr.setState("succ");
		if (ans == null || ans.getEmail() == null) {
			nr.setSeq(0);
			nr.setName("updateMyself");
			nr.setState("fail");
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	// 세션에 있는 아이디
	@ApiOperation(value = "현재 로그인되어있는 이메일의 비밀번호를 수정한다.", response = BoolResult.class)
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> updatePwd(@RequestBody User dto) throws Exception {
		logger.info("5-------------updatePwd-----------------------------" + dto);
		
		String newpwd=encoder.encode(dto.getPwd());
		dto.setPwd(newpwd);
		userService.updatePwd(dto);
		User ans = userService.findUserByEmail(dto.getEmail());
		UserseqResult nr = new UserseqResult();
		nr.setName("updatePwd");
		nr.setState("succ");
		if (ans == null || ans.getEmail() == null) {
			nr.setSeq(0);
			nr.setName("updatePwd");
			nr.setState("fail");
			return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
		}
		return new ResponseEntity<UserseqResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "탈퇴한다.", response = BoolResult.class)
	@RequestMapping(value = "/deleteMyself", method = RequestMethod.POST)
	public ResponseEntity<UserseqResult> deleteMyself(@RequestBody User dto) throws Exception {
		logger.info("6-------------deleteMyself-----------------------------" + dto);
		userService.deleteMyself(dto);
		User ans = userService.findUserByEmail(dto.getEmail());
		UserseqResult nr = new UserseqResult();
		nr.setName("deleteMyself");
		nr.setState("succ");
		if (ans == null || ans.getEmail() == null) {
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
	public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false, defaultValue = "0") String nowPage)
			throws Exception {
		logger.info("1-------------findAllUsers-----------------------------" + new Date());

		List<User> userList = userService.findAllUsers();
		System.out.println(userList);
		if (userList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			System.out.println(userList.get(0));
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일로 회원을 검색할 수 있다.", response = User.class)
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