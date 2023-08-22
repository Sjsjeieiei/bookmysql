package com.book.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.book.common.base.BaseController;
import com.book.goods.vo.GoodsVO;
import com.book.member.vo.MemberVO;
import com.book.order.service.ApiService01;
import com.book.order.service.OrderService;
import com.book.order.vo.OrderVO;
@Controller("orderController")
@RequestMapping(value = "/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderVO orderVO;

	@RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();
          // 로그인 상태가져옴
		Boolean isLogOn = (Boolean) session.getAttribute("isLogOn");
		String action = (String) session.getAttribute("action");
		if (isLogOn == null || isLogOn == false) { //null과 같거나 false 일시
			session.setAttribute("orderInfo", _orderVO); 
			session.setAttribute("action", "/order/orderEachGoods.do");
			return new ModelAndView("redirect:/member/loginForm.do");  // 로그온 상태가 null or false 일시 로그인 페이지로 넘어감.
		} else {
			if (action != null && action.equals("/order/orderEachGoods.do")) {
				orderVO = (OrderVO) session.getAttribute("orderInfo");      //널이 아닐경우 상품주문 페이지로 넘어감.
				session.removeAttribute("action");
			} else {
				orderVO = _orderVO;
			}
		}

		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		List myOrderList = new ArrayList<OrderVO>();
		myOrderList.add(orderVO);

		MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberInfo);
		return mav;
	}
//카트 전체 리스트 조회하기.
	@RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST)
	public ModelAndView orderAllCartGoods(@RequestParam("cart_goods_qty") String[] cart_goods_qty,    //카트 id
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		Map cartMap = (Map) session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		

		List<GoodsVO> myGoodsList = (List<GoodsVO>) cartMap.get("myGoodsList"); //상품리스트 가져옴.
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

		for (int i = 0; i < cart_goods_qty.length; i++) { 
			if(cart_goods_qty[i].contains(":")) {
				String[] cart_goods = cart_goods_qty[i].split(":");  //: 포함여부 확인.
				for (int j = 0; j < myGoodsList.size(); j++) { 
					GoodsVO goodsVO = myGoodsList.get(j);
					int goods_id = goodsVO.getGoods_id();
					if (goods_id == Integer.parseInt(cart_goods[0])) {
						OrderVO _orderVO = new OrderVO();
						String goods_title = goodsVO.getGoods_title();
						int goods_price = goodsVO.getGoods_price();
						String goods_fileName = goodsVO.getGoods_fileName();
						_orderVO.setGoods_id(goods_id);
						_orderVO.setGoods_title(goods_title);
						_orderVO.setGoods_price(goods_price);
						_orderVO.setGoods_fileName(goods_fileName);
						_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
						myOrderList.add(_orderVO);
						break;
					}
				}
				
			}
		}
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberVO);
		return mav;
	}
 //결재 완료.
	@RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST)
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
System.out.println("결재 완료까지 가져왔습니다.");
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("orderer"); //orderer 가져옴.
		String member_id = memberVO.getMember_id();
		String order_name = memberVO.getMember_name();
		String order_hp = memberVO.getHp1();
		List<OrderVO> myOrderList = (List<OrderVO>) session.getAttribute("myOrderList");

		for (int i = 0; i < myOrderList.size(); i++) { 
			OrderVO orderVO = (OrderVO) myOrderList.get(i);
			orderVO.setMember_id(member_id);
			orderVO.setReceiver_name(receiverMap.get("receiver_name"));
			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderVO.setDelivery_address(receiverMap.get("delivery_address"));

			orderVO.setPay_method(receiverMap.get("pay_method"));
			orderVO.setCard_com_name(receiverMap.get("card_com_name"));
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderVO.setPay_order_hp_num(receiverMap.get("pay_order_hp_num"));
			orderVO.setOrder_hp(order_hp);
			myOrderList.set(i, orderVO);
		} // end for

		orderService.addNewOrder(myOrderList);
		mav.addObject("myOrderInfo", receiverMap);
		mav.addObject("myOrderList", myOrderList);
		return mav;
	}

}