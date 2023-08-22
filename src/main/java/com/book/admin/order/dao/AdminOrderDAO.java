package com.book.admin.order.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.book.order.vo.OrderVO;


public interface AdminOrderDAO {
	public ArrayList<OrderVO> selectNewOrderList(Map condMap) throws DataAccessException;

	public void updateDeliveryState(Map deliveryMap) throws DataAccessException;
}
