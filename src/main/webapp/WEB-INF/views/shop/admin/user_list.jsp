<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/21/24
  Time: 17:25
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
            <a href="/admin/user_list.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                유저 관리
            </a>
            <a href="/admin/product_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                상품 관리
            </a>
            <a href="/admin/category_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                카테고리 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <div class="mb-6">
                <h2 class="text-2xl font-bold tracking-tight text-gray-900 text-center">회원 목록</h2>
            </div>

            <table class="w-full text-sm text-left rtl:text-right text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                <tr>
                    <th scope="col" class="px-6 py-3">
                        아이디
                    </th>
                    <th scope="col" class="px-6 py-3">
                        이름
                    </th>
                    <th scope="col" class="pl-6 py-3">
                        상태
                    </th>
                    <th scope="col" class="pl-6 py-3">
                        생성일자
                    </th>
                    <th scope="col" class="px-6 py-3">
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr class="bg-white even:bg-gray-50 border-b">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                            ${user.userID}
                        </th>
                        <td class="px-6 py-4">
                            ${user.userName}
                        </td>
                        <td class="pl-6 py-4">
                            ${user.state}
                        </td>
                        <td class="pl-6 py-4">
                            ${user.createdAt}
                        </td>
                        <td class="px-6 py-4 text-right">
                            <a href="#" class="text-right font-medium text-blue-600 hover:underline" data-te-toggle="modal"
                               data-te-target="#${user.userID}">상세보기 ></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="mt-6 w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
            <c:choose>
                <c:when test="${requestScope.userPage <= 10}">
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
                    <a href="/admin/user_list.do?user_page=${requestScope.userStartPage - 10}&user_pageSize=${requestScope.userPageSize}&admin_page=${requestScope.adminPage}&admin_pageSize=${requestScope.adminPageSize}">
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
                <c:forEach var="page" begin="${requestScope.userStartPage}" end="${requestScope.userLastPage}">
                    <c:choose>
                        <c:when test="${page eq requestScope.userPage}">
                            <a class="disabled text-sl font-medium leading-none cursor-pointer text-teal-700 border-t border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/admin/user_list.do?user_page=${page}&user_pageSize=${requestScope.userPageSize}&admin_page=${requestScope.adminPage}&admin_pageSize=${requestScope.adminPageSize}"
                               class="text-sl font-medium leading-none cursor-pointer text-gray-600 hover:text-teal-700 border-t border-transparent hover:border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <c:choose>
                <c:when test="${requestScope.userLastPage < requestScope.userTotalPage}">
                    <a href="/admin/user_list.do?user_page=${requestScope.userLastPage + 1}&user_pageSize=${requestScope.userPageSize}&admin_page=${requestScope.adminPage}&admin_pageSize=${requestScope.adminPageSize}">
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


        <div class="mb-6">
            <h2 class="text-2xl font-bold tracking-tight text-gray-900 text-center">관리자 목록</h2>
        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left rtl:text-right text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                <tr>
                    <th scope="col" class="px-6 py-3">
                        아이디
                    </th>
                    <th scope="col" class="px-6 py-3">
                        이름
                    </th>
                    <th scope="col" class="pl-6 py-3">
                        상태
                    </th>
                    <th scope="col" class="pl-6 py-3">
                        생성일자
                    </th>
                    <th scope="col" class="px-6 py-3">
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="admin" items="${requestScope.admins}">
                    <tr class="bg-white even:bg-gray-50 border-b">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                ${admin.userID}
                        </th>
                        <td class="px-6 py-4">
                                ${admin.userName}
                        </td>
                        <td class="pl-6 py-4">
                                ${admin.state}
                        </td>
                        <td class="pl-6 py-4">
                                ${admin.createdAt}
                        </td>
                        <td class="px-6 py-4 text-right">
                            <a href="#" class="text-right font-medium text-blue-600 hover:underline" data-te-toggle="modal"
                               data-te-target="#${admin.userID}">상세보기 ></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="mt-6 w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
            <c:choose>
                <c:when test="${requestScope.adminPage <= 10}">
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
                    <a href="/admin/user_list.do?user_page=${requestScope.userPage}&user_pageSize=${requestScope.userPageSize}&admin_page=${requestScope.adminPage - 10}&admin_pageSize=${requestScope.adminPageSize}">
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
                <c:forEach var="page" begin="${requestScope.adminStartPage}" end="${requestScope.adminLastPage}">
                    <c:choose>
                        <c:when test="${page eq requestScope.adminPage}">
                            <a class="disabled text-sl font-medium leading-none cursor-pointer text-teal-700 border-t border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/admin/user_list.do?user_page=${requestScope.userPage}&user_pageSize=${requestScope.userPageSize}&admin_page=${page}&admin_pageSize=${requestScope.adminPageSize}"
                               class="text-sl font-medium leading-none cursor-pointer text-gray-600 hover:text-teal-700 border-t border-transparent hover:border-teal-600 pt-3 mr-4 px-2">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <c:choose>
                <c:when test="${requestScope.adminLastPage < requestScope.adminTotalPage}">
                    <a href="/admin/user_list.do?user_page=${requestScope.userPage}&user_pageSize=${requestScope.userPageSize}&admin_page=${requestScope.adminLastPage + 1}&admin_pageSize=${requestScope.adminPageSize}">
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

        <!-- modal -->
        <c:forEach var="user" items="${requestScope.users}">
            <div
                    data-te-modal-init
                    class="absolute fixed left-0 top-0 z-[1055] hidden h-full w-full overflow-y-auto overflow-x-hidden outline-none"
                    id="${user.userID}"
                    tabindex="-1"
                    aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                <div
                        data-te-modal-dialog-ref
                        class="pointer-events-none relative w-auto translate-y-[-50px] opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:max-w-[500px]">
                    <div
                            class="min-[576px]:shadow-[0_0.5rem_1rem_rgba(#000, 0.15)] pointer-events-auto relative flex w-full flex-col rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600">
                        <div
                                class="flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50">
                            <!--Modal title-->
                            <h5
                                    class="text-xl font-medium leading-normal text-neutral-800 dark:text-neutral-200"
                                    id="exampleModalLabel">
                                유저 정보
                            </h5>
                        </div>

                        <!--Modal body-->
                        <div id="modal-body" class="relative flex-auto p-4" data-te-modal-body-ref>
                            <table class="w-full text-sm text-left rtl:text-right text-gray-500">
                                <tbody>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        아이디:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.userID}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        이름:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.userName}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        생년월일:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.userBirth}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        연락처:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.userTelephone}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        가입일자:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.createdAt}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        마지막 로그인 일자:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${user.latestLoginAt}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:forEach var="admin" items="${requestScope.admins}">
            <div
                    data-te-modal-init
                    class="absolute fixed left-0 top-0 z-[1055] hidden h-full w-full overflow-y-auto overflow-x-hidden outline-none"
                    id="${admin.userID}"
                    tabindex="-1"
                    aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                <div
                        data-te-modal-dialog-ref
                        class="pointer-events-none relative w-auto translate-y-[-50px] opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:max-w-[500px]">
                    <div
                            class="min-[576px]:shadow-[0_0.5rem_1rem_rgba(#000, 0.15)] pointer-events-auto relative flex w-full flex-col rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600">
                        <div
                                class="flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50">
                            <!--Modal title-->
                            <h5
                                    class="text-xl font-medium leading-normal text-neutral-800 dark:text-neutral-200"
                                    id="adminLabel">
                                관리자 정보
                            </h5>
                        </div>

                        <!--Modal body-->
                        <div class="relative flex-auto p-4" data-te-modal-body-ref>
                            <table class="w-full text-sm text-left rtl:text-right text-gray-500">
                                <tbody>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        아이디:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.userID}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        이름:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.userName}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        생년월일:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.userBirth}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        연락처:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.userTelephone}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        가입일자:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.createdAt}
                                    </td>
                                </tr>
                                <tr class="bg-white even:bg-gray-50 border-b">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                        마지막 로그인 일자:
                                    </th>
                                    <td class="text-center pl-6 py-4">
                                            ${admin.latestLoginAt}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </main>
</div>
</body>
</html>
