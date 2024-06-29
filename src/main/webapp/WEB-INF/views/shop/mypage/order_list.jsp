<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/21/24
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
               class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                주문내역
            </a>
            <a href="/mypage/point_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                포인트 내역 조회
            </a>
            <a href="/mypage/address_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                주소지 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <div class="bg-white shadow">
            <div class="px-4 py-6 sm:px-8 sm:py-10">
                <div class="flow-root">
                    <ul class="-my-8">
                        <c:forEach var="order" items="${requestScope.orders}">
                            <li class="flex flex-col space-y-3 py-6 text-left sm:flex-row sm:space-x-5 sm:space-y-0">
                                <div class="relative flex flex-1 flex-col justify-between">
                                    <div class="sm:col-gap-5 sm:grid sm:grid-cols-2">
                                        <div class="pr-8 sm:pr-5">
                                            <p class="text-base font-semibold text-gray-900">주문 일자: ${order.orderDate}</p>
                                            <p class="mx-0 mt-1 mb-0 text-sm text-gray-400">총 결제 금액: <fmt:formatNumber value="${order.price}"/>₩</p>
                                        </div>
                                    </div>

                                    <div class="absolute top-0 right-0 flex sm:bottom-0 sm:top-auto">
                                        <a href="/mypage/order/detail.do?order_id=${order.orderID}" class="inline-block text-blue-500 font-semibold py-2 px-4 rounded transition duration-300 ease-in-out">
                                            주문 상세보기 >
                                        </a>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <div class="mt-6 w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
            <c:choose>
                <c:when test="${requestScope.currentPage <= 10}">
                    <a class="invisible">
                        <div class="flex items-center pt-3 text-gray-600 hover:text-teal-700 cursor-pointer">
                            <svg width="14" height="8" viewBox="0 0 14 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1.1665 4H12.8332" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M1.1665 4L4.49984 7.33333" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M1.1665 4.00002L4.49984 0.666687" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <p class="text-sl ml-3 font-medium leading-none ">Previous</p>
                        </div>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/product/list.do?category=${requestScope.category}&page=${requestScope.startPage - 10}&pageSize=${requestScope.pageSize}">
                        <div class="flex items-center pt-3 text-gray-600 hover:text-teal-700 cursor-pointer">
                            <svg width="14" height="8" viewBox="0 0 14 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1.1665 4H12.8332" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M1.1665 4L4.49984 7.33333" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M1.1665 4.00002L4.49984 0.666687" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <p class="text-sl ml-3 font-medium leading-none ">Previous</p>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose>

            <div class="sm:flex hidden">
                <c:forEach var="page" begin="${requestScope.startPage}" end="${requestScope.lastPage}">
                    <c:choose>
                        <c:when test="${page eq requestScope.currentPage}">
                            <a class="disabled text-sl font-medium leading-none cursor-pointer text-teal-700 border-t border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/product/list.do?category=${requestScope.category}&page=${page}&pageSize=${requestScope.pageSize}"
                               class="text-sl font-medium leading-none cursor-pointer text-gray-600 hover:text-teal-700 border-t border-transparent hover:border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <c:choose>
                <c:when test="${requestScope.lastPage < requestScope.totalPage}">
                    <a href="/product/list.do?category=${requestScope.category}&page=${requestScope.lastPage + 1}&pageSize=${requestScope.pageSize}">
                        <div class="flex items-center pt-3 text-gray-600 hover:text-teal-700 cursor-pointer">
                            <p class="text-sl font-medium leading-none mr-3">Next</p>
                            <svg width="14" height="8" viewBox="0 0 14 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1.1665 4H12.8332" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9.5 7.33333L12.8333 4" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9.5 0.666687L12.8333 4.00002" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="invisible">
                        <div class="flex items-center pt-3 text-gray-600 hover:text-teal-700 cursor-pointer">
                            <p class="text-sl font-medium leading-none mr-3">Next</p>
                            <svg width="14" height="8" viewBox="0 0 14 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1.1665 4H12.8332" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9.5 7.33333L12.8333 4" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9.5 0.666687L12.8333 4.00002" stroke="currentColor" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</div>
</body>
</html>
