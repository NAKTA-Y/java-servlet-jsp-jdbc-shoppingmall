<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/21/24
  Time: 18:00
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
            <h2 class="pl-3 mb-4 text-2xl font-semibold">Management</h2>
            <a href="/admin/user_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                유저 관리
            </a>
            <a href="/admin/product_list.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                상품 관리
            </a>
            <a href="/admin/category_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                카테고리 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <ul class="-my-8">

            <c:forEach var="product" items="${requestScope.products}">
                <li class="flex flex-col space-y-3 py-6 text-left sm:flex-row sm:space-x-5 sm:space-y-0">
                    <div class="shrink-0">
                        <img class="h-24 w-24 max-w-full rounded-lg object-cover" src="${product.thumbnail}" alt="" />
                    </div>

                    <div class="relative flex flex-1 flex-col justify-between">
                        <div class="sm:col-gap-5 sm:grid sm:grid-cols-2">
                            <div class="pr-8 sm:pr-5">
                                <p class="text-base font-semibold text-gray-900">${product.modelNumber}</p>
                                <p class="mx-0 mt-1 mb-0 text-sm text-gray-400">${product.modelName}</p>
                                <p class="mx-0 mt-1 mb-0 text-sm text-gray-400">재고수량: ${product.stock}</p>
                            </div>

                            <div class="mt-4 flex items-end justify-between sm:mt-0 sm:items-start sm:justify-end">
                                <button type="button" onclick="location.href='/admin/product_delete.do?product_id=${product.productID}'" class="flex rounded p-2 text-center text-gray-500 transition-all duration-200 ease-in-out focus:shadow hover:text-gray-900">
                                    <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" class=""></path>
                                    </svg>
                                </button>
                            </div>
                        </div>

                        <div class="absolute top-0 right-3 flex sm:bottom-0 sm:top-auto">
                            <a href="/admin/product_info.do?product_id=${product.productID}" class="text-right font-medium text-blue-600 hover:underline">수정</a>
                        </div>
                    </div>
                </li>
            </c:forEach>

            <div class="mb-6 relative flex">
                <button type="button" onclick="location.href='/admin/product_create.do'" class="w-full text-white bg-gray-400 hover:bg-gray-500 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2">상품 추가하기</button>
            </div>
        </ul>

        <!-- pagination -->
        <div class="mt-12 w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
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
                    <a href="/admin/product_list.do?page=${requestScope.startPage - 10}&pageSize=${requestScope.pageSize}">
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
                            <a href="/admin/product_list.do?page=${page}&pageSize=${requestScope.pageSize}"
                               class="text-sl font-medium leading-none cursor-pointer text-gray-600 hover:text-teal-700 border-t border-transparent hover:border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <c:choose>
                <c:when test="${requestScope.lastPage < requestScope.totalPage}">
                    <a href="/admin/product_list.do?page=${requestScope.lastPage + 1}&pageSize=${requestScope.pageSize}">
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
