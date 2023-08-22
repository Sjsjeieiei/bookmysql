package com.book.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.book.goods.dao.GoodsDAO;
import com.book.goods.vo.GoodsVO;
import com.book.goods.vo.ImageFileVO;
@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDAO goodsDAO;

	public Map<String, List<GoodsVO>> listGoods() throws Exception {
		Map<String, List<GoodsVO>> goodsMap = new HashMap<String, List<GoodsVO>>();

		//테이블의 bestseller 상품
		List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
		goodsMap.put("bestseller", goodsList);

		//문학
		goodsList = goodsDAO.selectMenusList("문학");
		goodsMap.put("cate_mun", goodsList);

		//it
		goodsList = goodsDAO.selectMenusList("it");
		goodsMap.put("cate_it", goodsList);

		//만화
		goodsList = goodsDAO.selectMenusList("만화");
		goodsMap.put("cate_man", goodsList);

		//웹툰
		goodsList = goodsDAO.selectMenusList("웹툰");
		goodsMap.put("cate_web", goodsList);

		//카테고리 목록 Map return
		return goodsMap;
	}

	
	
	//header 메뉴
	@Override
	public List<GoodsVO> menuGoods(String menuGoods) throws Exception {
		List goodsList = goodsDAO.selectGoodsByMenuGoods(menuGoods);
		return goodsList;
	}
	
	

	//키워드검색
	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list = goodsDAO.selectKeywordSearch(keyword);
		return list;
	}

	
	//상품검색
	@Override
	public List<GoodsVO> searchGoods(String searchWord) throws Exception {
		List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}
	

	//상세 구매 페이지
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap = new HashMap();
		//결과 담을 goodsMap 생성
		
		
		//goodsmap 리스트에 goodsvo라는 이름을 추가합니다.
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		goodsMap.put("goodsVO", goodsVO);
		
	    // 상품에 대한 이미지 파일 정보를 가져와 goodsMap에 "imageList"라는 이름으로 추가합니다.
		List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
		goodsMap.put("imageList", imageList);
		
		//최종 데이터 저장된 goodsMap을 반환합니다.
		return goodsMap;
	}

}
