package com.book.order.service;

import java.util.List;

import com.book.order.vo.OrderVO;


public interface OrderService {

	//주문하기 - 결제완료후 주문 table에 insert 된다
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception;
}
