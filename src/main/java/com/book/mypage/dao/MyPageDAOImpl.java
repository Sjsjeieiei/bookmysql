package com.book.mypage.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.book.member.vo.MemberVO;
import com.book.order.vo.OrderVO;

@Repository("myPageDAO")
public class MyPageDAOImpl implements MyPageDAO{
	@Autowired
	private SqlSession sqlSession;

	//내 주문조회 목록
	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException{
		List<OrderVO> myOrderHistList=(List)sqlSession.selectList("mapper.mypage.selectMyOrderHistoryList",dateMap);
		return myOrderHistList;
	}
	
	//주문취소
	public void updateMyOrderCancel(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderCancel",order_id);
	}
	
	// 주문취소 값 돌려줌.
	public void updateMyOrderReturn(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderReturn",order_id);
	}
	
	//주문 상태변경.
	public void updateMyOrderExchange(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderExchange",order_id);
	}
	
	//내 데이터 가져옴.
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException{
		MemberVO memberVO=(MemberVO)sqlSession.selectOne("mapper.mypage.selectMyDetailInfo",member_id);
		return memberVO;
		
	}
	
	//데이터 업데이트
	public void updateMyInfo(Map memberMap) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyInfo",memberMap);
	}
	
	//삭제
	public void deleteMember(String member_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.deleteMember",member_id);
	}
//주문 목록
	@Override
	public List<OrderVO> selectMyOrderGoodsList(String member_id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	// 주문 선택
	@Override
	public List selectMyOrderInfo(String order_id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override//삭제
	public void delmember(String member_id) throws DataAccessException {
		// TODO Auto-generated method stub
		sqlSession.update("mapper.mypage.deleteMember",member_id);
	}
	
	
	
	
}
