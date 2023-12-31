package com.book.goods.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.book.goods.vo.GoodsVO;
import com.book.goods.vo.ImageFileVO;

public interface GoodsDAO {
	public List<GoodsVO> selectGoodsList(String goodsStatus ) throws DataAccessException;
	public List<String> selectKeywordSearch(String keyword) throws DataAccessException;
	public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException;
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;
	public List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException;
	List<GoodsVO> selectMenusList(String goodsSort) throws DataAccessException;
	List<GoodsVO> selectGoodsByMenuGoods(String menuGoods) throws DataAccessException;
}
