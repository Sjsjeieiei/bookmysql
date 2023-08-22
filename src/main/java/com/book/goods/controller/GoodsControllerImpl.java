package com.book.goods.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.book.common.base.BaseController;
import com.book.goods.service.GoodsService;
import com.book.goods.vo.GoodsVO;

import net.sf.json.JSONObject;
@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	@Autowired
	private GoodsService goodsService;
	
// 상품 메뉴
	@Override
	@RequestMapping(value="menuGoods.do" ,method = RequestMethod.GET)
	public ModelAndView menuGoods(String menuGoods, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		List<GoodsVO> goodsList=goodsService.menuGoods(menuGoods);
		ModelAndView mav = new ModelAndView(viewName);
		//goodsList 추가
		//menuGoods 추가
		mav.addObject("goodsList", goodsList);
		mav.addObject("menuGoods", menuGoods);
		return mav;
	}
	

	//키워드 선택.
	@RequestMapping(value="/keywordSearch.do",method = RequestMethod.GET,produces = "application/text; charset=utf8")
	public @ResponseBody String  keywordSearch(@RequestParam("keyword") String keyword,  // 리퀘스트 keyword 받아옴.
			                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		//keywordr가 null 이거나 공백일떄 null 을 반환한다.
		if(keyword == null || keyword.equals(""))
		   return null ;
	
		//대문자로 변환
		keyword = keyword.toUpperCase();
	    List<String> keywordList =goodsService.keywordSearch(keyword);
	    //문자열 리스트 keywordList 로 키워드 선택
		JSONObject jsonObject = new JSONObject(); //json객체 생성하고
		jsonObject.put("keyword", keywordList); //key :keyword value:keywordList 로 내보낸다.
	    String jsonInfo = jsonObject.toString();
	    
	    //return jsonInfo 
	    return jsonInfo ;
	}
	
	
	//�˻�
	@RequestMapping(value="/searchGoods.do" ,method = RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		List<GoodsVO> goodsList=goodsService.searchGoods(searchWord);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsList", goodsList);
		return mav;
	}
	
	
	//상품 상세
	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		
		//상품 goodsDetail 메소드를 호출하여 goods id를 토대로 상품정보를 goodsMap에 저장합니다.
		Map goodsMap=goodsService.goodsDetail(goods_id);
		mav.addObject("goodsMap", goodsMap);
		
		
		//goodsvo라고 저장된 객채를 가지고옴.
		
		GoodsVO goodsVO=(GoodsVO)goodsMap.get("goodsVO");
		
		//빠른 주문 리스트에 추가합니다.
		addGoodsInQuick(goods_id,goodsVO,session);
		
		return mav;
	}
	
	
	
// 빠른구매
	private void addGoodsInQuick(String goods_id,GoodsVO goodsVO,HttpSession session){
		// 중복 여부검사 
		boolean already_existed=false;
		
		   // 빠른 상품 리스트 quickGoodsList를 가져옵니다.
		List<GoodsVO> quickGoodsList;
		quickGoodsList=(ArrayList<GoodsVO>)session.getAttribute("quickGoodsList");
		
		//이미 존재할경우 
		if(quickGoodsList!=null){
			
			//빠른 상품이 3이상으로 존재한다면
			if(quickGoodsList.size() < 3){
				for(int i=0; i<quickGoodsList.size();i++){
					String _goodsBean=String.valueOf(quickGoodsList.get(i).getGoods_id());
					   // quickGoodsList에서 i번째 상품의 goods_id를 문자열로 변환하여 _goodsBean 변수에 저장합니다.
					
					
					// goods_id 와 _goodsBean 과 같으면 이미 있다는 뜻이므로 already_existed=true; 반복문을 탈출한다.
					if(goods_id.equals(_goodsBean)){
						already_existed=true;
						break;
					}
				}
				  // already_existed가 false인 경우, 중복이 아닌 상품을 빠른 상품 리스트에 추가합니다.
				if(already_existed==false){
					quickGoodsList.add(goodsVO);
				}

			//3개 이상인경우
			}else {
				//첫 번쨰 상품 제거
				quickGoodsList.remove(0);
				quickGoodsList.add(goodsVO);
			}
		
		
		//빠른 상품 리스트가 존재하지 않는경우  새로운 ArrayList를 생성한다.
		}else{
			quickGoodsList =new ArrayList<GoodsVO>();
			quickGoodsList.add(goodsVO);
		}
		
		//빠른 상품 리스트와 리스트 크기를 세션에 적용합니다.
		session.setAttribute("quickGoodsList",quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
	
}