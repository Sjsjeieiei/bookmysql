package com.book.mypage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.book.member.vo.MemberVO;
import com.book.order.vo.OrderVO;


public interface MyPageService{
	//�ֹ����
		public List<OrderVO> listMyOrderHistory(Map dateMap) throws Exception;

		//�ֹ����
		public void cancelOrder(String order_id) throws Exception;

		//��ǰ
		public void returnOrder(String order_id) throws Exception;
		
		//��ȯ
		public void exchangeOrder(String order_id) throws Exception;

		//������
		public MemberVO myDetailInfo(String member_id) throws Exception;
		
		//�� ���� ����
		public MemberVO  modifyMyInfo(Map memberMap) throws Exception;
		
		//ȸ��Ż��
		public void  deleteMember(String member_id) throws Exception;
}
