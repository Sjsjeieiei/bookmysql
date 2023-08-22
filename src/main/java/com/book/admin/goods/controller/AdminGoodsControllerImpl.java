package com.book.admin.goods.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.book.admin.goods.service.AdminGoodsService;
import com.book.common.base.BaseController;
import com.book.goods.vo.GoodsVO;
import com.book.goods.vo.ImageFileVO;
import com.book.member.vo.MemberVO;



@Controller("adminGoodsController")
@RequestMapping(value = "/admin/goods")
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController {
	private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";
	@Autowired
	private AdminGoodsService adminGoodsService;

	@RequestMapping(value = "/adminGoodsMain.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		session = request.getSession();

		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		String beginDate = null, endDate = null;

		
		String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);

		Map<String, Object> condMap = new HashMap<String, Object>();
		
		if(section== null) {
			section = "1";
		}
		condMap.put("section",section);
		if(pageNum== null) {
			pageNum = "1";
		}
		condMap.put("pageNum",pageNum);

		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
		mav.addObject("newGoodsList", newGoodsList);

		String beginDate1[] = beginDate.split("-");
		String endDate2[] = endDate.split("-");
		mav.addObject("beginYear", beginDate1[0]);
		mav.addObject("beginMonth", beginDate1[1]);
		mav.addObject("beginDay", beginDate1[2]);
		mav.addObject("endYear", endDate2[0]);
		mav.addObject("endMonth", endDate2[1]);
		mav.addObject("endDay", endDate2[2]);

		mav.addObject("section", section);
		mav.addObject("pageNum", pageNum);
		return mav;

	}

	// 상품추가
		@RequestMapping(value = "/addNewGoods.do", method = { RequestMethod.POST })
		public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
				throws Exception {
			multipartRequest.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			
			String imageFileName = null;
			
			//form 값을 받아 newGoodsMap에 put
			Map newGoodsMap = new HashMap();
			Enumeration enu = multipartRequest.getParameterNames();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String value = multipartRequest.getParameter(name);
				newGoodsMap.put(name, value);
			}

			//세션에서 get한 memberInfo가 reg_id, 수정한이가 됨.
			HttpSession session = multipartRequest.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
			String reg_id = memberVO.getMember_id();

			//baseController upload
			List<ImageFileVO> imageFileList = upload(multipartRequest);
			
			//imageFileList를 받아 setReg_id해 newGoodsMap에 put
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileVO.setReg_id(reg_id);
				}
				newGoodsMap.put("imageFileList", imageFileList);
			}

			String message = null;
			ResponseEntity resEntity = null;
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html; charset=utf-8");
			
			try {
				//상품, 파일정보가 들어있는 newGoodsMap으로 addNewGoods 수행
				int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
				
				//imageFileList가 있을 경우 
				if (imageFileList != null && imageFileList.size() != 0) {
					for (ImageFileVO imageFileVO : imageFileList) {
						
						//temp안에 imageFileName 이름으로 파일 생성,
						imageFileName = imageFileVO.getFileName();
						File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
						
						//이후 goods_id가 폴더명인 폴더를 하나 만들어 
						File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
						
						//이동한다.
						FileUtils.moveFileToDirectory(srcFile, destDir, true);
					}
				}
				
				//위의 절차가 완료되면 안내하며, adminGoodsMain 상품목록 페이지를 reload한다.
				message = "<script>";
				message += " alert('새상품을 추가했습니다.');";
				message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
				message += ("</script>");
			} catch (Exception e) {
				//예외발생의 경우 
				//이미 파일이 생성되어 있는 이후 에러가 발생시 대비한다.
				if (imageFileList != null && imageFileList.size() != 0) {
					for (ImageFileVO imageFileVO : imageFileList) {
						imageFileName = imageFileVO.getFileName();
						File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
						//만들어진 temp경로안의 파일들을 삭제한다.
						srcFile.delete();
					}
				}
				//삭제를 완료한 이후 안내하며, adminGoodsMain 상품목록 페이지를 reload한다.
				message = "<script>";
				message += " alert('정상작동 상품추가');";
				message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
				message += ("</script>");
				e.printStackTrace();
			}
			
			
			//각 경우에 따른 message를 가지고 resEntity 리턴한다.
			resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
			return resEntity;
		}
		
	

	@RequestMapping(value = "/modifyGoodsForm.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView modifyGoodsForm(@RequestParam("goods_id") int goods_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		Map goodsMap = adminGoodsService.goodsDetail(goods_id);
		mav.addObject("goodsMap", goodsMap);

		return mav;
	}
	@RequestMapping(value = "/addNewGoodsForm.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addNewGoodsForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

	@RequestMapping(value = "/modifyGoodsInfo.do", method = { RequestMethod.POST })
	public ResponseEntity modifyGoodsInfo(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName = null;

		// form 값을 받아 newGoodsMap에 put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

		// 수령받은 imageFileList의 정보에서 getFileName이 비어잇진 않는지 확인하고, 카운트한다.
		int check = 0;
		List<ImageFileVO> imageFileList = upload(multipartRequest);
		if (imageFileList != null && imageFileList.size() != 0) {
			for (ImageFileVO imageFileVO : imageFileList) {
				if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {
					check += 1;
				}
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			// modifyGoods 상품정보 ㅅ정
			adminGoodsService.modifyGoods(goods_id, newGoodsMap);
			for (ImageFileVO imageFileVO : imageFileList) {

				// 수령받은 정보에서 getFileName을 찾을 수 없다면 폴더/이미지를 생성/업로드하지않는다.
				if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {
				} else {
					// 리스트와 파일정보를 잘 받았다면
					imageFileName = imageFileVO.getFileName();
					// temp 임시폴더 안에 파일생성 imageFileName
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					// 이름이 goods_id인 폴더로 덮어쓰기 copyFileToDirectory
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					FileUtils.copyFileToDirectory(srcFile, destDir);
				}

			}

			// 위 절차를 완료한 이후 안내하며 adminGoodsMain로 reload
			message = "<script>";
			message += " alert('수정되었습니다!');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");

		} catch (Exception e) {
			// 수정중 예외가 낫을때

			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					// temp임시 폴더에 생성된 파일들을 삭제한다.
					srcFile.delete();
				}
			}

			// 위 절차를 완료한 이후 안내하며 adminGoodsMain로 reload
			message = "<script>";
			message += " alert('상품이 정상적으로 추가되셨습니다.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			e.printStackTrace();
		}

		// 각 경우에 따른 message를 가지고 resEntity 리턴한다.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value = "/addNewGoodsImage.do", method = { RequestMethod.POST })
	public void addNewGoodsImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		System.out.println("addNewGoodsImage");
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName = null;

		Map goodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			goodsMap.put(name, value);
		}

		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();

		List<ImageFileVO> imageFileList = null;
		int goods_id = 0;
		try {
			imageFileList = upload(multipartRequest);
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String) goodsMap.get("goods_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setReg_id(reg_id);
				}

				adminGoodsService.addNewGoodsImage(imageFileList);
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
		} catch (Exception e) {
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value = "/deleteGoods.do", method = { RequestMethod.POST,RequestMethod.GET })
	public ModelAndView deleteGoods(@RequestParam("goods_id") int goods_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		adminGoodsService.deleteGoods(goods_id);
		File folder = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
		try {
			while (folder.exists()) {
				File[] folder_list = folder.listFiles();
				for (int j = 0; j < folder_list.length; j++) {
					folder_list[j].delete();
				}
				if (folder_list.length == 0 && folder.isDirectory()) {
					folder.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/goods/adminGoodsMain.do");
		return mav;
	}

}

