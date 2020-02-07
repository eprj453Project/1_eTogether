package com.ssafy.edu.springjwt.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.service.IUserService;
import com.ssafy.edu.service.UserService;
import com.ssafy.edu.springjwt.model.ERole;
import com.ssafy.edu.springjwt.model.Role;
import com.ssafy.edu.controller.UserController;
import com.ssafy.edu.model.User;
import com.ssafy.edu.springjwt.payload.request.LoginRequest;
import com.ssafy.edu.springjwt.payload.request.SignupRequest;
import com.ssafy.edu.springjwt.payload.response.JwtResponse;
import com.ssafy.edu.springjwt.payload.response.MessageResponse;
import com.ssafy.edu.springjwt.security.jwt.JwtUtils;
import com.ssafy.edu.springjwt.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("Start signin");

		// db에 있는지 확인
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPwd()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication); // 토큰을 만들어서 jwt에 넣는다.

		// 지금 로그인된 정보를 userDetails에 넣는다.
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
	}

/////////////////내꺼에 맞게 바꾸기
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userService.findUserByEmail(signUpRequest.getEmail()) != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		// Create new user's account
		User user = new User(0, signUpRequest.getEmail(), encoder.encode(signUpRequest.getPwd()), null,
				signUpRequest.getName(), 0, 1, signUpRequest.getPhone());

//		String phone = user.getPhone();
//		String newphone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11);
//		user.setPhone(newphone);

		int result = userService.regi(user);

		if (result != 1) {
			System.out.println("Signup fail");
		} else {
			System.out.println("Signup Success");
		}
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}