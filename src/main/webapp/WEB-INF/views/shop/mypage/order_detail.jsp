<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/24/24
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="min-h-screen mt-16 py-14 px-4 md:px-6 2xl:px-20 2xl:container 2xl:mx-auto">
    <!--- more free and premium Tailwind CSS components at https://tailwinduikit.com/ --->

    <div class="flex justify-start item-start space-y-2 flex-col">
        <h1 class="text-3xl dark:text-white lg:text-4xl font-semibold leading-7 lg:leading-9 text-gray-800">주문/결제</h1>
    </div>
    <div class="mt-10 flex flex-col xl:flex-row jusitfy-center items-stretch w-full xl:space-x-8 space-y-4 md:space-y-6 xl:space-y-0">
        <div class="flex flex-col justify-start items-end w-full space-y-4 md:space-y-6 xl:space-y-8">
            <div class="flex flex-col justify-start items-start bg-gray-50 px-4 py-4 md:py-6 md:p-6 xl:p-8 w-full">
                <p class="text-lg md:text-xl font-semibold leading-6 xl:leading-5 text-gray-800">장바구니 내역</p>

                <c:forEach var="orderDetail" items="${requestScope.orderDetails}">
                    <div class="mt-4 md:mt-6 flex flex-col md:flex-row justify-start items-start md:items-center md:space-x-6 xl:space-x-8 w-full">
                        <div class="pb-4 md:pb-8 w-full md:w-40">
                            <img class="rounded-xl w-full hidden md:block" src="${requestScope.productMap[orderDetail.productID].thumbnail}" />
                            <img class="rounded-xl w-full md:hidden" src="${requestScope.productMap[orderDetail.productID].thumbnail}" />
                        </div>
                        <div class="border-b border-gray-200 md:flex-row flex-col flex justify-between items-start w-full pb-14 space-y-4 md:space-y-0">
                            <div class="w-full flex flex-col justify-start items-start space-y-4">
                                <h3 class="text-xl xl:text-2xl font-semibold leading-6 text-gray-800">${requestScope.productMap[orderDetail.productID].modelName}</h3>
                                <div class="flex justify-start items-start flex-col space-y-2">
                                    <p class="text-sm leading-none text-gray-800"><span class="text-gray-300"></span>${requestScope.productMap[orderDetail.productID].brand}</p>
                                </div>
                            </div>
                            <div class="flex justify-end space-x-8 items-start w-full">
                                <p class="mr-36 text-base dark:text-white xl:text-lg leading-6 text-gray-800">${orderDetail.quantity}</p>
                                <p class="text-base dark:text-white xl:text-lg font-semibold leading-6 text-gray-800"><fmt:formatNumber value="${orderDetail.quantity * orderDetail.unitPrice}"/>₩</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="flex justify-center flex-col md:flex-row flex-col items-stretch w-full space-y-4 md:space-y-0 md:space-x-6 xl:space-x-8">
                <div class="flex flex-col px-4 py-6 md:p-6 xl:p-8 w-full bg-gray-50 space-y-6">
                    <h3 class="mb-6 text-xl font-semibold leading-5 text-gray-800">Summary</h3>
                    <div class="flex justify-between w-full">
                        <p class="text-base font-semibold leading-4 text-gray-800">합계</p>
                        <p class="text-base font-semibold leading-4 text-gray-600"><fmt:formatNumber value="${requestScope.order.price}"/>₩</p>
                    </div>
                </div>
                <div class="flex flex-col px-4 py-6 md:p-6 xl:p-8 w-full bg-gray-50 space-y-6">
                    <div class="flex justify-between">
                        <h3 class="text-xl font-semibold leading-5 text-gray-800">Address</h3>
                    </div>
                            <div class="flex justify-center items-center w-full space-y-4 flex-col pb-4">
                                <div class="flex justify-between w-full">
                                    <p class="text-base leading-4 text-gray-800">주소:</p>
                                    <p id="selectedAddress" class="text-base leading-4 text-gray-600">${requestScope.address.address}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">상세 주소:</p>
                                    <p id="selectedAddressDetail" class="text-base leading-4 text-gray-600">${requestScope.address.addressDetail}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">받는이 이름:</p>
                                    <p id="selectedName" class="text-base leading-4 text-gray-600">${requestScope.address.name}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">받는이 연락처:</p>
                                    <p id="selectedTelephone" class="text-base leading-4 text-gray-600">${requestScope.address.telephone}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">요청사항: </p>
                                    <p id="selectedRequest" class="text-base leading-4 text-gray-600">${requestScope.address.request}</p>
                                </div>
                            </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.js"></script>
</body>
</html>
