package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.MemberDto;

public interface MemberDao {
	
	public MemberDto login(MemberDto memberDto) throws SQLException;
	
	public List<MemberDto> listUser() throws SQLException;
	// 현재 로그인한 회원 정보
	public MemberDto getUserInfo(String userid) throws SQLException; 
	// id 중복체크
	public int idCheck(String userid) throws SQLException; 
	
	public int insertMember(MemberDto memberDto) throws SQLException;
	public int updateMember(MemberDto memberDto) throws SQLException;
	public int deleteMember(int user_no) throws SQLException;
	public void deleteBookMarkOfMember(int user_no) throws SQLException; 
}
