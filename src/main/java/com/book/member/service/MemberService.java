package com.book.member.service;

import java.util.Map;

import com.book.member.vo.MemberVO;


public interface MemberService {
	public MemberVO login(Map  loginMap) throws Exception;
	public void addMember(MemberVO memberVO) throws Exception;
	public String overlapped(String id) throws Exception;
//	public void logout(MemberVO memberVO) throws Exception;
}
