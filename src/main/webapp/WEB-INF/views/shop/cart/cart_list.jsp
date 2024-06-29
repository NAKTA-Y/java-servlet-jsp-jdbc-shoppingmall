<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/24/24
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="w-full min-h-screen py-1 mt-12 pt-20">
    <h1 class="mb-10 text-center text-2xl font-bold">장바구니</h1>
    <div class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
        <div class="rounded-lg md:w-2/3">
            <c:forEach var="cart" items="${requestScope.shoppingCarts}">
                <div class="relative justify-between mb-6 rounded-lg bg-white p-6 shadow-md sm:flex sm:justify-start">
                    <img src="${requestScope.productMap[cart.productID].thumbnail}" alt="product-image" class="w-full rounded-lg sm:w-40" />
                    <div class="mt-7 sm:ml-4 sm:flex sm:w-full sm:justify-between">
                        <div class="mt-5 sm:mt-0">
                            <h2 class="text-lg font-bold text-gray-900">${requestScope.productMap[cart.productID].modelName}</h2>
                            <p class="mt-1 text-xs text-gray-700">${requestScope.productMap[cart.productID].brand}</p>
                            <p id="stock" class="mt-1 text-xs text-gray-700">재고수량 : ${requestScope.productMap[cart.productID].stock}</p>
                        </div>
                        <div class="mt-4 flex justify-between sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
                            <div class="ml-3 flex items-center border-gray-100">
                                <button onclick="location.href='/cart/minus_quantity.do?product_id=${cart.productID}'" type="button" class="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50"> - </button>
                                <p class="h-8 w-8 border bg-white text-center flex justify-center items-center text-xs outline-none">${cart.quantity}</p>
                                <button onclick="location.href='/cart/plus_quantity.do?product_id=${cart.productID}&stock=${requestScope.productMap[cart.productID].stock}'" type="button" class="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50"> + </button>
                            </div>
                            <div class="flex items-center space-x-4">
                                <p class="text-sm"><fmt:formatNumber value="${requestScope.productMap[cart.productID].sale * cart.quantity}"/>₩</p>
                            </div>
                        </div>
                    </div>
                    <button onclick="location.href='/cart/delete.do?product_id=${cart.productID}'" type="button" class="absolute top-0 right-0 bg-white rounded-md p-2 inline-flex items-center justify-center text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500">
                        <span class="sr-only">Close menu</span>
                        <!-- Heroicon name: outline/x -->
                        <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                        </svg>
                    </button>
                </div>
            </c:forEach>
        </div>

        <!-- Sub total -->
        <div class="mt-6 h-full rounded-lg border bg-white p-6 shadow-md md:mt-0 md:w-1/3 sticky top-52">
            <div class="mb-2 flex justify-between">
                <p class="text-gray-700">총 금액</p>
                <p class="text-gray-700"><fmt:formatNumber value="${requestScope.totalPrice}"/>₩</p>
            </div>
            <div class="flex justify-between">
                <p class="text-gray-700">할인 금액</p>
                <p class="text-gray-700">-<fmt:formatNumber value="${requestScope.totalPrice - requestScope.totalSale}"/>₩</p>
            </div>
            <hr class="my-4" />
            <div class="flex justify-between">
                <p class="text-lg font-bold">합계</p>
                <div class="">
                    <p class="mb-1 text-lg font-bold"><fmt:formatNumber value="${requestScope.totalSale}"/>₩</p>
                </div>
            </div>
            <c:if test="${not empty requestScope.shoppingCarts}">
                <button type="button" onclick="location.href='/order/summary.do'" class="mt-6 w-full rounded-md bg-teal-600 py-1.5 font-medium text-teal-50 hover:bg-teal-700">결제하기</button>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
