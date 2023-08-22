package com.book.admin.goods.service;

import java.util.List;
import java.util.Map;

import com.book.goods.vo.GoodsVO;
import com.book.order.vo.OrderVO;

public interface AdminGoodsService {
	public int  addNewGoods(Map newGoodsMap) throws Exception;
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception;
	public Map goodsDetail(int goods_id) throws Exception;
	public List goodsImageFile(int goods_id) throws Exception;
	public List<OrderVO> listOrderGoods(Map condMap) throws Exception;
	public void deleteGoods(int goods_id) throws Exception;
	public void addNewGoodsImage(List imageFileList) throws Exception;
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception;
}
