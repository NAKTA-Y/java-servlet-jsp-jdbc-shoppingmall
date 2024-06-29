<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/23/24
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="mt-32 bg-white w-full flex flex-col gap-5 px-3 md:px-16 lg:px-28 md:flex-row text-[#161931]">
    <aside class="hidden py-4 md:w-1/3 lg:w-1/4 md:block">
        <div class="sticky flex flex-col gap-2 p-4 text-sm border-r border-indigo-100 top-12">

            <h2 class="pl-3 mb-4 text-2xl font-semibold">Settings</h2>

            <a href="/mypage/info.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                개인정보 수정
            </a>
            <a href="/mypage/change_password.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                비밀번호 변경
            </a>
            <a href="/mypage/order_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                주문내역
            </a>
            <a href="/mypage/point_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                포인트 내역 조회
            </a>
            <a href="/mypage/address_list.do"
               class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                주소지 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <form action="${requestScope.action}" method="POST">
            <input hidden name="address_id" value="${requestScope.address_id}">
            <div class="ml-10 w-2/3">
                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="name">
                        받는분 이름
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           id="name"
                           name="name"
                           type="text"
                           value="${requestScope.address.name}"
                           placeholder="홍길동">
                </div>
                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="phone">
                        받는분 연락처
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           name="phone"
                           id="phone"
                           type="text"
                           value="${requestScope.address.telephone}"
                           placeholder="010-1234-1234">
                </div>

                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="address">
                        주소
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           name="address"
                           id="address"
                           type="text"
                           value="${requestScope.address.address}"
                           placeholder="서울시 강남구 테헤란로 69길 5 (삼성동, DT센터)">
                </div>

                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="address_detail">
                        상세주소
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           name="address_detail"
                           id="address_detail"
                           type="text"
                           value="${requestScope.address.addressDetail}"
                           placeholder="5동 502호">
                </div>

                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="zipcode">
                        우편번호
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           name="zipcode"
                           id="zipcode"
                           type="text"
                           value="${requestScope.address.zipcode}"
                           placeholder="52212">
                </div>

                <div class="mb-6">
                    <label class="block text-gray-700 text-lg font-bold mb-2" for="request">
                        배송 요청사항
                    </label>
                    <input required
                           class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           name="request"
                           id="request"
                           value="${requestScope.address.request}"
                           type="text">
                </div>

                <div class="mb-6 inline-flex items-center">
                    <label class="relative flex items-center pr-3 rounded-full cursor-pointer" htmlFor="check">
                        <input type="checkbox"
                               name="default"
                               class="before:content[''] peer relative h-5 w-5 cursor-pointer appearance-none rounded-md border border-blue-gray-200 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-gray-900 checked:bg-gray-900 checked:before:bg-gray-900 hover:before:opacity-10"
                               ${requestScope.address.defaultAddress ? 'checked' : ''}
                               id="check" />
                        <span
                                class="absolute text-white transition-opacity opacity-0 pointer-events-none top-2/4 left-1/4 -translate-y-2/4 -translate-x-1/4 peer-checked:opacity-100">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-3.5 w-3.5" viewBox="0 0 20 20" fill="currentColor"
           stroke="currentColor" stroke-width="1">
        <path fill-rule="evenodd"
              d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
              clip-rule="evenodd"></path>
      </svg>
    </span>
                    </label>
                    <label class="mt-px text-gray-700 cursor-pointer select-none" htmlFor="check">
                        기본 배송지 설정
                    </label>
                </div>

                <div class="flex mt-6 justify-end">
                    <button type="submit" class="relative inline-flex items-center justify-center p-4 px-6 py-3 overflow-hidden font-medium text-indigo-600 transition duration-300 ease-out border-2 border-green-700 rounded-full shadow-md group">
          <span class="absolute inset-0 flex items-center justify-center w-full h-full text-white duration-300 -translate-x-full bg-green-700 group-hover:translate-x-0 ease">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path></svg>
          </span>
                        <c:choose>
                            <c:when test="${requestScope.type eq 'create'}">
                                <span class="absolute flex items-center justify-center w-full h-full text-green-700 transition-all duration-300 transform group-hover:translate-x-full ease">주소지 추가</span>
                                <span class="relative invisible">주소지 추가</span>
                            </c:when>
                            <c:otherwise>
                                <span class="absolute flex items-center justify-center w-full h-full text-green-700 transition-all duration-300 transform group-hover:translate-x-full ease">수정 완료</span>
                                <span class="relative invisible">수정 완료</span>
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </div>
        </form>
    </main>
</div>
</body>
</html>
