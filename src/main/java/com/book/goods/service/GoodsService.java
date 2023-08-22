package com.book.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.book.goods.vo.GoodsVO;


public interface GoodsService {
	
	public Map<String,List<GoodsVO>> listGoods() throws Exception;
	public Map goodsDetail(String _goods_id) throws Exception;
	
	public List<String> keywordSearch(String keyword) throws Exception;
	public List<GoodsVO> searchGoods(String searchWord) throws Exception;
	List<GoodsVO> menuGoods(String menuGoods) throws Exception;
	
}
