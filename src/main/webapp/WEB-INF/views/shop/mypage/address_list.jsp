<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/23/24
  Time: 18:21
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
            <a href="/mypage/address_list.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                주소지 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <div class="rounded-lg bg-white shadow">
            <div class="px-4 py-6 sm:px-8 sm:py-10">
                <div class="flow-root">
                    <ul class="-my-8">
                        <c:forEach var="address" items="${requestScope.addresses}">
                            <li class="flex flex-col space-y-3 py-6 text-left sm:flex-row sm:space-x-5 sm:space-y-0">
                                <div class="relative flex flex-1 flex-col justify-between">
                                    <div class="sm:grid-cols-2">
                                        <div class="pr-8 sm:pr-5">
                                            <div class="flex justify-between">
                                                <a href="#" class="flex text-base font-semibold text-gray-900">낙타
                                                    <c:choose>
                                                        <c:when test="${address.defaultAddress}">
                                                            <p class="mb-2 text-slate-600">
                                                                <span class="ml-2 bg-teal-600 rounded-lg text-sm text-white p-1">기본배송지</span>
                                                            </p>

                                                        </c:when>
                                                        <c:otherwise>
                                                            <p class="invisible mb-2 text-slate-600">
                                                                <span class="ml-2 bg-teal-600 rounded-lg text-sm text-white p-1">기본배송지</span>
                                                            </p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>

                                                <div class="flex justify-end items-center">
                                                    <button type="button" onclick="location.href='/mypage/address_edit.do?address_id=${address.addressID}';" class="text-white bg-gray-400 hover:bg-gray-500 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-2 py-1 me-2 mb-2">수정</button>
                                                    <button type="button" onclick="location.href='/mypage/address_delete.do?address_id=${address.addressID}';" class="text-white bg-red-700 hover:bg-red-800 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-2 py-1 me-2 mb-2">삭제</button>
                                                </div>
                                            </div>
                                            <p class="mx-0 mt-1 mb-0 text-sm text-gray-400">010-1234-1234</p>
                                            <p class="mx-0 mt-1 mb-0 text-sm text-gray-500">서울시 강남구 테헤란로 69길 5 (삼성동, DT센터) 502</p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <div class="ml-1 mt-6 relative flex">
            <button type="button" onclick="location.href='/mypage/address_create.do'" class="w-full text-white bg-gray-400 hover:bg-gray-500 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2">주소지 추가하기</button>
        </div>
    </main>
</div>
</body>
</html>
