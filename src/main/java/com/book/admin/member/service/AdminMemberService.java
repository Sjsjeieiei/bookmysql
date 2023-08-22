package com.book.admin.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.book.member.vo.MemberVO;


public interface AdminMemberService {
	public ArrayList<MemberVO> listMember(HashMap condMap) throws Exception;
}
