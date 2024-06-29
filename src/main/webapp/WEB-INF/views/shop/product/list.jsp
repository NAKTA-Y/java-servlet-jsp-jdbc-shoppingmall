<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/20/24
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>

<div class="flex items-center justify-center lg:px-0 sm:px-6 px-4">
    <!--- more free and premium Tailwind CSS components at https://tailwinduikit.com/ --->
    <div class="bg-white w-full">
        <div class="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">

            <div class="flex overflow-x-auto overflow-y-hidden whitespace-nowrap dark:border-gray-700 justify-center mb-12">
                <c:choose>
                    <c:when test="${empty requestScope.get('category')}">
                        <a href="/product/list.do"
                           class="inline-flex items-center h-10 px-2 py-2 -mb-px text-center text-teal-700 bg-transparent border-b-2 border-teal-600 sm:px-4 -px-1 whitespace-nowrap focus:outline-none">
                            <span class="mx-1 text-sm sm:text-base"> 전체 </span>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/product/list.do"
                           class="inline-flex items-center h-10 px-2 py-2 -mb-px text-center text-gray-700 bg-transparent border-b-2 border-transparent sm:px-4 -px-1 dark:text-white whitespace-nowrap cursor-base focus:outline-none hover:border-gray-400">
                            <span class="mx-1 text-sm sm:text-base"> 전체 </span>
                        </a>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="category" items="${requestScope.categories}">
                    <c:choose>
                        <c:when test="${category.categoryID eq requestScope.category}">
                            <a href="/product/list.do?category=${category.categoryID}"
                               class="inline-flex items-center h-10 px-2 py-2 -mb-px text-center text-teal-700 bg-transparent border-b-2 border-teal-600 sm:px-4 -px-1 whitespace-nowrap focus:outline-none">
                                <span class="mx-1 text-sm sm:text-base"> ${category.categoryName} </span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/product/list.do?category=${category.categoryID}"
                               class="inline-flex items-center h-10 px-2 py-2 -mb-px text-center text-gray-700 bg-transparent border-b-2 border-transparent sm:px-4 -px-1 dark:text-white whitespace-nowrap cursor-base focus:outline-none hover:border-gray-400">
                                <span class="mx-1 text-sm sm:text-base"> ${category.categoryName} </span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <div class="min-h-screen mt-6 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                <c:forEach var="product" items="${requestScope.products}">
                    <div class="group relative">
                        <div class="border rounded-xl aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:shadow-xl lg:h-70 duration-300">
                            <img src="${product.thumbnail}" alt="Front of men&#039;s Basic Tee in black." class="h-full w-full object-cover object-center lg:h-full lg:w-full">
                        </div>
                        <div class="mt-4 flex justify-between">
                            <div>
                                <h3 class="text-xl text-gray-700">
                                    <a href="/product/detail.do?product_id=${product.productID}">
                                        <span aria-hidden="true" class="absolute inset-0"></span>
                                            ${product.modelName}
                                    </a>
                                </h3>
                                <div class="items-right flex mt-2">
                                    <p class="text-lg font-medium text-gray-500 text-decoration-line: line-through"><fmt:formatNumber value="${product.price}"/>₩</p>
                                    <p class="ml-2 text-lg font-medium text-gray-900"><fmt:formatNumber value="${product.sale}"/>₩</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<div class="w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
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

<script src="/WEB-INF/views/shop/product/script.js"></script>