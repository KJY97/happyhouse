package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.MemberDao;
import com.ssafy.happyhouse.model.dto.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {
	
	private static MemberDao memberDao;
	private static MemberService memberService = new MemberServiceImpl(memberDao);
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return memberDao.login(memberDto);
	}
	
	@Override
	public List<MemberDto> listUser() throws Exception {
		return memberDao.listUser();
	}

	@Override
	public MemberDto getUserInfo(String userid) throws Exception {
		return memberDao.getUserInfo(userid);
	}
	
	@Override
	public boolean idCheck(String userid) throws Exception {
		return memberDao.idCheck(userid) == 0; // 중복이 아님
	}

	@Override
	@Transactional
	public boolean insertMember(MemberDto memberDto) throws Exception {
		return memberDao.insertMember(memberDto) == 1;
	}
	
	@Override
	@Transactional
	public boolean updateMember(MemberDto memberDto) throws Exception {
		return memberDao.updateMember(memberDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteMember(int user_no) throws Exception {
		memberDao.deleteBookMarkOfMember(user_no);
		return memberDao.deleteMember(user_no) == 1;
	}
}
