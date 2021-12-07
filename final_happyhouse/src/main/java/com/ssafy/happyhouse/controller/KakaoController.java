package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.KakaoAPI;
import com.ssafy.happyhouse.model.service.MemberService;

@RestController
@RequestMapping("/login")
public class KakaoController {
	
	public static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private KakaoAPI kakao;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/auth", produces = "application/json")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("code") String AUTHORIZE_CODE) {
		String access_Token = kakao.getAccessToken(AUTHORIZE_CODE); // 인가코드를 통해 카카오 토큰 발급
		MemberDto memberDto = kakao.getUserInfo(access_Token); // 토큰에서 사용자 정보 MemberDto타입으로 가져오기
	    System.out.println("login Controller : " + memberDto);
	    
	    // 이전에 회원가입을 한 적이 있는지, DB에 있다면 우리 사이트 전용 토큰 발급    
	    Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			MemberDto loginUser = kakao.login(memberDto); // 해당 유저정보 DB에 있는지 확인
			if (loginUser != null) {
				// 토큰 생성
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				
				System.out.println("로그인 새로운 token 발급 : " + token);
				status = HttpStatus.ACCEPTED; // 202. 요청 수신 but, 응하여 행동할 수 없음
				
			} else {// 유저 정보가 없으므로 회원가입 하기
				
				if(memberDto.getUserid() == null) { // email 정보가 없다!!
					resultMap.put("message", "write_email"); 
					
				} else if(memberService.insertMember(memberDto)) {
					String token = jwtService.create("userid", kakao.login(memberDto).getUserid(), "access-token");// key, data, subject
					resultMap.put("access-token", token);
					resultMap.put("message", SUCCESS); 
					
					System.out.println("회원가입 새로운 token 발급 : " + token);

				} else {
					System.out.println("회원가입 실패!!!");
					resultMap.put("message", FAIL); 
				}
				
				status = HttpStatus.ACCEPTED;
			}
			
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR; // 500. 서버가 처리 방법을 모름
		}
		
		System.out.println("resultMap : " + resultMap);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
