package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.MemberService;

@RestController
@RequestMapping("/user")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	/**
	 * 로그인 : Access-token과 로그인 결과 메세지를 반환
	 * @param memberDto : 필요한 회원정보(아이디, 비밀번호)
	 * @return : message 내용과 HttpStatus 리턴
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				// 토큰 생성
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED; // 202. 요청 수신 but, 응하여 행동할 수 없음
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR; // 500. 서버가 처리 방법을 모름
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	/**
	 * 회원인증 : 회원 정보를 담은 Token을 반환
	 * @param userid : 인증할 회원의 아이디
	 * @param request : request 값 받아오기
	 * @return : message 내용과 HttpStatus 리턴
	 */
	@GetMapping("/{userid}")
	public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable("userid") String userid, HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED; 
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보
				MemberDto memberDto = memberService.getUserInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED; 
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR; 
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/ckid/{userid}")
	private ResponseEntity<String> idCheck(@PathVariable("userid") String userid) throws Exception {
		logger.debug("아이디 중복 체크");
		if(memberService.idCheck(userid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} 
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	private ResponseEntity<String> insertMember(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("회원가입 : {}", memberDto);
		System.out.println(memberDto);
		Map<String, Object> resultMap = new HashMap<>();
		if (memberService.insertMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	private ResponseEntity<String> updateMember(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("회원정보 수정 : {}", memberDto);
		if (memberService.updateMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} 
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{user_no}")
	private ResponseEntity<String> deleteMember(@PathVariable("user_no") int user_no) throws Exception {
		logger.info("회원 탈퇴");
		if (memberService.deleteMember(user_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
