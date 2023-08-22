package com.book.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.book.member.vo.MemberVO;


@Repository("adminMemberDAO")
public class AdminMemberDAOImpl implements AdminMemberDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException {
		ArrayList<MemberVO>  memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMember",condMap);
		return memberList;
	}


	
	
}
