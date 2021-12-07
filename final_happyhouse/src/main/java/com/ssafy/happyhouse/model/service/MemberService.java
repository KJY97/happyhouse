package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	
	public List<MemberDto> listUser() throws Exception;
	public MemberDto getUserInfo(String userid) throws Exception;
	public boolean idCheck(String userid) throws Exception;
	
	public boolean insertMember(MemberDto memberDto) throws Exception;
	public boolean updateMember(MemberDto memberDto) throws Exception;
	public boolean deleteMember(int user_no) throws Exception;
}
