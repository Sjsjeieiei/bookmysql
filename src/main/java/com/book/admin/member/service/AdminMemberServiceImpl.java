package com.book.admin.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.book.admin.member.dao.AdminMemberDAO;
import com.book.member.vo.MemberVO;


@Service("adminMemberService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminMemberServiceImpl implements AdminMemberService {
	@Autowired
	private AdminMemberDAO adminMemberDAO;

	@Override
	public ArrayList<MemberVO> listMember(HashMap condMap) throws Exception {
		return adminMemberDAO.listMember(condMap);
	}




}
