<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
    request.setCharacterEncoding("UTF-8");
%>
<style>
    .popup {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
    }

    .popup-content {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: white;
        padding: 20px;
        border-radius: 5px;
    }

    .close-button {
        position: absolute;
        top: 10px;
        right: 10px;
        font-size: 20px;
        cursor: pointer;
    }
</style>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const popup = document.getElementById("popup");
        const closePopupButton = document.getElementById("closePopupButton");

        // 이미 팝업을 닫았는지 확인
        const isPopupClosed = localStorage.getItem("popupClosed");

        // 이미 팝업을 닫지 않았다면
        if (!isPopupClosed) {
            // 일정 시간 후에 팝업 창을 자동으로 보이게 함 (예: 3초 후)
            setTimeout(function () {
                popup.style.display = "block";
            }, 3000);
        }

        closePopupButton.addEventListener("click", function () {
            popup.style.display = "none";

            // 팝업을 닫은 정보를 로컬 스토리지에 저장
            localStorage.setItem("popupClosed", "true");
        });
    });
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<c:if test="${not empty sessionScope.alertMessage}">
    <script>
        alert("${sessionScope.alertMessage}");
    </script>
    <c:remove var="alertMessage" scope="session"/>
</c:if>
<script>
    $(document).ready(function () {
        // 슬라이드 기능을 위한 탭 버튼을 선택합니다.
        var tabButtons = $('.hero .tab-content .tab-pane');

        // 첫 번째 탭 버튼을 활성화합니다.
        tabButtons.first().addClass('show active');

        // 탭 버튼들을 순환하여 일정 간격으로 활성화/비활성화합니다.
        var currentTabIndex = 0;
        var interval = 6000; // 각 슬라이드가 보여지는 시간 (3초)

        function activateNextTab() {
            tabButtons.eq(currentTabIndex).removeClass('show active');
            currentTabIndex = (currentTabIndex + 1) % tabButtons.length;
            tabButtons.eq(currentTabIndex).addClass('show active');
        }

        // 슬라이드 탭을 일정 간격으로 변경합니다.
        var slideInterval = setInterval(activateNextTab, interval);

        // 마우스 오버 시 슬라이드가 멈추도록 합니다.
        $('.hero').on('mouseenter', function () {
            clearInterval(slideInterval);
        }).on('mouseleave', function () {
            slideInterval = setInterval(activateNextTab, interval);
        });
    });
</script>
<style>
    /* Custom styles for spacing */
    .spacer {
        height: 20px; /* Adjust the height as needed */
        width: 100%; /* Ensure full width */
        display: block;
        content: "";
    }
</style>
<!-- hero섹션, 광고영역 -->
<div class="hero" style="height: 450px">
    <div class="tab-content container h130 p-0 position-relative" id="">

        <!-- tabCaller 버튼영역 -->
        <a class="tab-pane show active d-block" id="hero01" role="tabpanel"
           aria-labelledby="hero1"
           href="${contextPath}/goods/goodsDetail.do?goods_id=420"> <img
                src="${contextPath}/resources/img/dada/2.jpg" class="today-image"
                data-log-props="" data-adsplatform=""
                style="display: block; z-index: 1; position: absolute;">
        </a> <a class="tab-pane" id="hero02" role="tabpanel"
                aria-labelledby="hero2"
                href="${contextPath}/goods/goodsDetail.do?goods_id=1500"> <img
            src="${contextPath}/resources/img/dada/1.jpg" class="today-image"
            data-log-props="" data-adsplatform=""
            style="display: block; z-index: 1; position: absolute;">
    </a> <a class="tab-pane" id="hero03" role="tabpanel"
            aria-labelledby="hero3"
            href="${contextPath}/goods/goodsDetail.do?goods_id=200"> <img
            src="${contextPath}/resources/img/dada/3.jpg" class="today-image"
            data-log-props="" data-adsplatform=""
            style="display: block; z-index: 1; position: absolute;">
    </a> <a class="tab-pane" id="hero04" role="tabpanel"
            aria-labelledby="hero4"
            href="${contextPath}/goods/goodsDetail.do?goods_id=1300"> <img
            src="${contextPath}/resources/img/dada/4.jpg" class="today-image"
            data-log-props="" data-adsplatform=""
            style="display: block; z-index: 1; position: absolute;">
    </a> <a class="tab-pane" id="hero05" role="tabpanel"
            aria-labelledby="hero5"
            href="${contextPath}/goods/goodsDetail.do?goods_id=1600"> <img
            src="${contextPath}/resources/img/dada/5.png" class="today-image"
            data-log-props="" data-adsplatform=""
            style="display: block; z-index: 1; position: absolute;">
    </a>
        <!-- tabCaller 버튼영역 -->


        <!-- tab본문영역, caller 선택시 id값에 맞는 tab이 표출된다. -->
        <div
                class="list-group position-absolute top-50 end-0 translate-middle-y z-2 me-5 rounded-0"
                role="tablist">
            <%-- 			<a class="list-group-item list-group-item-action p-0 active"
                            id="hero1" data-bs-toggle="list" href="#hero01" role="tab"
                            aria-controls="hero01"> <img
                            src="${contextPath}/resources/img/cate/1.png">
                        </a> <a class="list-group-item list-group-item-action p-0" id="hero2"
                            data-bs-toggle="list" href="#hero02" role="tab"
                            aria-controls="hero02"> <img
                            src="${contextPath}/resources/img/cate/2.png">
                        </a> <a class="list-group-item list-group-item-action p-0" id="hero3"
                            data-bs-toggle="list" href="#hero03" role="tab"
                            aria-controls="hero03"> <img
                            src="${contextPath}/resources/img/cate/3.png">
                        </a> <a class="list-group-item list-group-item-action p-0" id="hero4"
                            data-bs-toggle="list" href="#hero04" role="tab"
                            aria-controls="hero04"> <img
                            src="${contextPath}/resources/img/cate/4.png">
                        </a> <a class="list-group-item list-group-item-action p-0" id="hero5"
                            data-bs-toggle="list" href="#hero05" role="tab"
                            aria-controls="hero05"> <img
                            src="${contextPath}/resources/img/cate/5.png">
                        </a> --%>
        </div>
        <!-- tab본문영역, caller 선택시 id값에 맞는 tab이 표출된다. -->

    </div>
</div>
<!-- hero섹션, 광고영역 -->


<!-- sopang추천 광고영역 -->
<div class="container">
    <div class="row">
        <div class="d-flex p-0 align-items-center gap-3 mt-5">
            <p class="fs-3 fw-bold">오늘의 책</p>
            <p class="fs-6">| 오늘 북숍이 엄선한 베스트셀러 상품!</p>
        </div>
    </div>
    <div class="row border p-3">
        <div class="d-flex p-0 align-items-center gap-3 flex-wrap bestseller">
            <c:set var="goods_count" value="0"/>
            <!-- goodsMap 상품리스트 중, goods_status의 값이  bestseller로 지정된 리스트만 추출해 뿌린다.  -->
            <c:forEach var="item" items="${goodsMap.bestseller}">
                <c:set var="goods_count" value="${goods_count+1 }"/>
                <a class="back_eee position-relative"
                   href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }">
                    <img class="position-absolute top-50 start-50 translate-middle"
                         src="${contextPath}/download.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}">
                </a>
            </c:forEach>
            <!-- goodsMap 상품리스트 중, goods_status의 값이  bestseller로 지정된 리스트만 추출해 뿌린다.  -->
        </div>
    </div>
</div>
<!-- sopang추천 광고영역 -->


<!-- sopang 카테고리 광고영역 -->
<div class="container">
    <div class="row">
        <div class="p-0 align-items-center gap-3 mt-5">
            <p class="fs-6 mb-1">HOT! TREND</p>
            <p class="fs-3 fw-bold">베스트셀러 상품</p>
        </div>
    </div>

    <div class="row position-relative">


        <!-- 	tab Caller
         <div
                class="list-group position-absolute bottom-0 start-0 z-2 rounded-0 mb-4"
                role="tablist" style="width: 150px">
                <a
                    class="list-group-item list-group-item-action active btn mb-2 rounded-0 border-main samll"
                    id="trend1" data-bs-toggle="list" href="#trend01" role="tab"
                    aria-controls="trend01">문학</a> <a
                    class="list-group-item list-group-item-action btn mb-2 rounded-0 border-main samll"
                    id="trend2" data-bs-toggle="list" href="#trend02" role="tab"
                    aria-controls="trend02">it</a> <a
                    class="list-group-item list-group-item-action btn mb-2 rounded-0 border-main samll"
                    id="trend3" data-bs-toggle="list" href="#trend03" role="tab"
                    aria-controls="trend03">만화</a> <a
                    class="list-group-item list-group-item-action btn mb-2 rounded-0 border-main samll"
                    id="trend4" data-bs-toggle="list" href="#trend04" role="tab"
                    aria-controls="trend04">웹툰</a>
            </div> -->

        <!-- tab 본문영역 -->

        <div class="spacer"></div> <!-- Spacing div with custom style -->

        <!-- Your content continues here -->

        <div class="spacer"></div> <!-- More spacing if needed -->


        <div class="tab-content border-top border-main border-2 p-0">


            <div class="tab-pane fade show active" id="trend01" role="tabpanel" aria-labelledby="trend1">
                <div class="d-flex justify-content-between">
                    <div class="pt-4 pe-5 flex-grow-1 box-sixing-content" style="width: 200px">
                        <a href="${contextPath}/goods/menuGoods.do?goodsSort=문학" class="text-decoration-none">
                            <p class="fw-bold fs-4 mb-1">전체상품</p>
                            <span class="small">보러가기 > </span>
                        </a>
                    </div>
                    <div class="position-relative">
                        <img src="${contextPath}/resources/img/book.png">
                        <a href="${contextPath}/goods/menuGoods.do?goodsSort=it"
                           class="btn btn-main rounded-0 position-absolute bottom-0 start-50 translate-middle z-2 rounded-0 py-3 w100 d-block"
                           style="width: 80%">
                            <p class="mb-0 fw-bold">추천상품</p>
                        </a>
                    </div>
                    <div class="d-flex p-4 pe-0 pb-0 categoryTabGoodList align-items-start flex-wrap"
                         style="gap: 1.8rem !important;">
                        <c:forEach var="item" items="${goodsMap.cate_mun}" end="5">
                            <c:set var="goods_count" value="${goods_count+1}"/>
                            <div>
                                <a class="text-decoration-none"
                                   href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id}">
                                    <div class="back_eee">
                                        <img src="${contextPath}/download.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}">
                                    </div>
                                    <p class="mt-2 mb-1 text-truncate">${item.goods_title}</p>
                                    <p>
                                <span class="fw-bold">
                                    <fmt:formatNumber value="${item.goods_price}" pattern="#,###"/>
                                </span>원
                                    </p>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <!-- 추천 만화   -->
            <div class="container">
                <div class="row">
                    <div class="d-flex p-0 align-items-center gap-3 mt-5">
                        <p class="fs-3 fw-bold">추천 만화</p>
                                          </div>
                </div>

                    <div class="tab-content border-top border-main border-2 p-0">
                        <div class="row border p-4">
                            <div class="row mt-4 justify-content-center bg-light-gray">
                                <c:forEach var="item" items="${goodsMap.cate_man}">
                                    <c:if test="${item.goods_sort eq '만화'}">
                                        <div class="col-6 col-md-4 col-lg-3 mb-4">
                                            <div class="card">
                                                <a href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id}">
                                                    <img class="card-img-top mx-auto"
                                                         src="${contextPath}/download.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}"
                                                         alt="${item.goods_title}">
                                                </a>
                                                <div class="card-body text-center">
                                                    <h5 class="card-title">${item.goods_title}</h5>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="mb-5">
                                    <a href="/goods/menuGoods.do?menuGoods=만화"
                                       class="btn btn-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mb-3">바로가기</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="popup" class="popup">
                        <div class="popup-content">
                            <span class="close-button" id="closePopupButton">&times;</span>
                            <a href="${contextPath}/goods/goodsDetail.do?goods_id=420">
                                <img src="${contextPath}/resources/img/op3.jpg" alt="Popup Image" width="500"
                                     height="400">
                            </a>
                        </div>
                    </div>


                </div>
