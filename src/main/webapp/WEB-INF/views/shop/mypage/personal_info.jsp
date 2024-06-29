<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/21/24
  Time: 14:59
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
               class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
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
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                주소지 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <div class="p-2 md:p-4">
            <div class="w-full px-6 pb-8 mt-8 sm:max-w-xl sm:rounded-lg">
                <form action="/mypage/infoUpdate.do" method="POST">
                <div class="grid max-w-2xl mx-auto mt-8">
                    <h2 class="text-2xl font-bold sm:text-xl">개인정보 수정</h2>

                    <div class="items-center mt-8 sm:mt-14 text-[#202142]">
                        <div class="mb-2 sm:mb-6">
                            <label for="name"
                                   class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">이름:</label>
                            <input type="text" id="name"
                                   class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 "
                                   name="name"
                                   value="${sessionScope.user.userName}"
                                   required>
                        </div>

                        <div class="mb-2 sm:mb-6">
                            <label for="birth"
                                   class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">생년월일:</label>
                            <input type="text" id="birth"
                                   class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 "
                                   name="birth"
                                   value="${sessionScope.user.userBirth}"
                                   required>
                        </div>

                        <div class="mb-2 sm:mb-6">
                            <label for="phone"
                                   class="block mb-2 text-sm font-medium text-indigo-900 dark:text-white">연락처:</label>
                            <input type="tel" id="phone"
                                   class="bg-indigo-50 border border-indigo-300 text-indigo-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 "
                                   name="phone"
                                   value="${sessionScope.user.userTelephone}"
                                   required>
                        </div>

                        <div class="flex justify-between">
                            <button type="button" onclick="location.href='/mypage/user_delete.do'"
                                    class="text-white bg-red-700  hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">탈퇴</button>
                            <button type="submit"
                                    class="text-white bg-indigo-700  hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">수정</button>
                        </div>

                    </div>
                </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
