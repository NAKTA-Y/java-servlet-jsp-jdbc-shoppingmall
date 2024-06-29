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
    <form id="orderForm" action="/order/payment.do", method="POST">
    <div class="mt-10 flex flex-col xl:flex-row jusitfy-center items-stretch w-full xl:space-x-8 space-y-4 md:space-y-6 xl:space-y-0">
        <div class="flex flex-col justify-start items-end w-full space-y-4 md:space-y-6 xl:space-y-8">
            <div class="flex flex-col justify-start items-start bg-gray-50 px-4 py-4 md:py-6 md:p-6 xl:p-8 w-full">
                <p class="text-lg md:text-xl font-semibold leading-6 xl:leading-5 text-gray-800">장바구니 내역</p>

                <input type="hidden" name="carts" value="${fn:escapeXml(requestScope.cartsJson)}">
                <c:forEach var="cart" items="${requestScope.shoppingCarts}">
                    <div class="mt-4 md:mt-6 flex flex-col md:flex-row justify-start items-start md:items-center md:space-x-6 xl:space-x-8 w-full">
                        <div class="pb-4 md:pb-8 w-full md:w-40">
                            <img class="rounded-xl w-full hidden md:block" src="${requestScope.productMap[cart.productID].thumbnail}" />
                            <img class="rounded-xl w-full md:hidden" src="${requestScope.productMap[cart.productID].thumbnail}" />
                        </div>
                        <div class="border-b border-gray-200 md:flex-row flex-col flex justify-between items-start w-full pb-14 space-y-4 md:space-y-0">
                            <div class="w-full flex flex-col justify-start items-start space-y-4">
                                <h3 class="text-xl xl:text-2xl font-semibold leading-6 text-gray-800">${requestScope.productMap[cart.productID].modelName}</h3>
                                <div class="flex justify-start items-start flex-col space-y-2">
                                    <p class="text-sm leading-none text-gray-800"><span class="text-gray-300"></span>${requestScope.productMap[cart.productID].brand}</p>
                                </div>
                            </div>
                            <div class="flex justify-end space-x-8 items-start w-full">
                                <p class="mr-36 text-base dark:text-white xl:text-lg leading-6 text-gray-800">${cart.quantity}</p>
                                <p class="text-base dark:text-white xl:text-lg font-semibold leading-6 text-gray-800"><fmt:formatNumber value="${requestScope.productMap[cart.productID].sale * cart.quantity}"/>₩</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="flex justify-center flex-col md:flex-row flex-col items-stretch w-full space-y-4 md:space-y-0 md:space-x-6 xl:space-x-8">
                <div class="flex flex-col px-4 py-6 md:p-6 xl:p-8 w-full bg-gray-50 space-y-6">
                    <h3 class="mb-6 text-xl font-semibold leading-5 text-gray-800">Summary</h3>
                    <div class="flex justify-center items-center w-full space-y-4 flex-col border-gray-200 border-b pb-4">
                        <div class="flex justify-between w-full">
                            <p class="text-base dark:text-white leading-4 text-gray-800">총 금액</p>
                            <p class="text-base dark:text-gray-300 leading-4 text-gray-600"><fmt:formatNumber value="${requestScope.totalPrice}"/>₩</p>
                        </div>
                        <input type="hidden" name="total_price" value="${requestScope.totalSale}">
                        <div class="flex justify-between items-center w-full">
                            <p class="text-base leading-4 text-gray-800">할인 금액
                            <p class="text-base leading-4 text-gray-600">-<fmt:formatNumber value="${requestScope.totalPrice - requestScope.totalSale}"/>₩</p>
                        </div>

                    </div>
                    <div class="flex justify-between items-center w-full">
                        <p class="text-base font-semibold leading-4 text-gray-800">합계</p>
                        <p class="text-base font-semibold leading-4 text-gray-600"><fmt:formatNumber value="${requestScope.totalSale}"/>₩</p>
                    </div>
                </div>
                <div class="flex flex-col px-4 py-6 md:p-6 xl:p-8 w-full bg-gray-50 space-y-6">
                    <div class="flex justify-between">
                        <h3 class="text-xl font-semibold leading-5 text-gray-800">Address</h3>

                        <button type="button" id="dropdownRadioHelperButton" data-dropdown-toggle="dropdownRadioHelper"
                                class="text-white bg-teal-700 hover:bg-teal-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-3.5 py-1.5 text-center inline-flex items-center" type="button">주소 선택하기<svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="m1 1 4 4 4-4"/>
                        </svg>
                        </button>

                        <!-- Dropdown menu -->
                        <div id="dropdownRadioHelper" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-60 dark:bg-gray-700 dark:divide-gray-600">
                            <ul class="p-3 space-y-1 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownRadioHelperButton">
                                <c:forEach var="address" items="${requestScope.addresses}">
                                    <li>
                                        <div class="flex p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                                            <div class="address-item flex items-center h-5"
                                                 data-name="${address.name}"
                                                 data-address="${address.address}"
                                                 data-address-detail="${address.addressDetail}"
                                                 data-telephone="${address.telephone}"
                                                 data-request="${address.request}">

                                                <c:choose>
                                                    <c:when test="${address eq requestScope.defaultAddress}">
                                                        <input id="address_id-${address.addressID}"
                                                               name="address_id"
                                                               type="radio"
                                                               value="${address.addressID}"
                                                               class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500"
                                                               checked
                                                        >
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input id="address_id-${address.addressID}"
                                                               name="address_id"
                                                               type="radio"
                                                               value="${address.addressID}"
                                                               class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500"
                                                        >
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="ms-2 text-sm">
                                                <label for="address_id-${address.addressID}" class="font-medium text-gray-900 dark:text-gray-300">
                                                    <div>${address.name}</div>
                                                    <p id="address_id-text-4" class="text-xs font-normal text-gray-500 dark:text-gray-300">${address.address} | ${address.addressDetail}</p>
                                                </label>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                            <div class="flex justify-center items-center w-full space-y-4 flex-col pb-4">
                                <div class="flex justify-between w-full">
                                    <p class="text-base leading-4 text-gray-800">주소:</p>
                                    <p id="selectedAddress" class="text-base leading-4 text-gray-600">${requestScope.defaultAddress.address}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">상세 주소:</p>
                                    <p id="selectedAddressDetail" class="text-base leading-4 text-gray-600">${requestScope.defaultAddress.addressDetail}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">받는이 이름:</p>
                                    <p id="selectedName" class="text-base leading-4 text-gray-600">${requestScope.defaultAddress.name}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">받는이 연락처:</p>
                                    <p id="selectedTelephone" class="text-base leading-4 text-gray-600">${requestScope.defaultAddress.telephone}</p>
                                </div>
                                <div class="flex justify-between items-center w-full">
                                    <p class="text-base leading-4 text-gray-800">요청사항: </p>
                                    <p id="selectedRequest" class="text-base leading-4 text-gray-600">${requestScope.defaultAddress.request}</p>
                                </div>
                            </div>
                </div>
            </div>

            <div class="w-1/4 flex justify-between">
                <button type="button" onclick="location.href='/product/list.do'" class="rounded-lg mx-1 hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-800 py-5 w-96 md:w-full bg-gray-300 text-base font-medium leading-4 text-white">돌아가기</button>
                <button class="rounded-lg mx-1 hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-800 py-5 w-96 md:w-full bg-gray-600 text-base font-medium leading-4 text-white">결제하기</button>
            </div>
        </div>
    </div>
    </form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.2.1/flowbite.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const radioButtons = document.querySelectorAll('input[name="address_id"]');

        radioButtons.forEach(radioButton => {
            radioButton.addEventListener('change', () => {
                if (radioButton.checked) {
                    const addressDiv = radioButton.closest('.address-item');
                    const name = addressDiv.getAttribute('data-name');
                    const address = addressDiv.getAttribute('data-address');
                    const addressDetail = addressDiv.getAttribute('data-address-detail');
                    const telephone = addressDiv.getAttribute('data-telephone');
                    const request = addressDiv.getAttribute('data-request');

                    // Update Address Section
                    document.getElementById('selectedName').innerText = name;
                    document.getElementById('selectedAddress').innerText = address;
                    document.getElementById('selectedAddressDetail').innerText = addressDetail;
                    document.getElementById('selectedTelephone').innerText = telephone;
                    document.getElementById('selectedRequest').innerText = request;
                }
            });
        });
    });
</script>
</body>
</html>
