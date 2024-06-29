<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/23/24
  Time: 17:00
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

            <h2 class="pl-3 mb-4 text-2xl font-semibold">Management</h2>
            <a href="/admin/user_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                유저 관리
            </a>
            <a href="/admin/product_list.do"
               class="flex items-center px-3 py-2.5 font-semibold  hover:text-indigo-900 hover:border hover:rounded-full">
                상품 관리
            </a>
            <a href="/admin/category_list.do" class="flex items-center px-3 py-2.5 font-bold bg-white  text-indigo-900 border rounded-full">
                카테고리 관리
            </a>
        </div>
    </aside>
    <main class="w-full min-h-screen py-1 md:w-2/3 lg:w-3/4">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <div class="mb-6">
                <h2 class="text-2xl font-bold tracking-tight text-gray-900 text-center">카테고리 목록</h2>
            </div>

            <table class="w-full text-sm text-left rtl:text-right text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                <tr>
                    <th scope="col" class="w-1/4 px-6 py-3">
                        카테고리 번호
                    </th>
                    <th scope="col" class="px-6 py-3">
                        카테고리 이름
                    </th>
                    <th scope="col" class="px-6 py-3">
                    </th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <tr class="bg-white border-b hover:bg-gray-50">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                ${category.categoryID}
                            </th>
                            <td class="px-6 py-4">
                                ${category.categoryName}
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a href="#" class="text-right font-medium text-blue-600 hover:underline" data-te-toggle="modal"
                                   data-te-target="#category-${category.categoryID}">수정</a>
                                <em class="text-md">|</em>
                                <a href="/admin/category_delete.do?category_id=${category.categoryID}" class="text-right font-medium text-red-600 hover:underline">삭제</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="mt-6 relative flex">
            <button type="button"
                    data-te-toggle="modal"
                    data-te-target="#category-create"
                    class="w-full text-white bg-gray-400 hover:bg-gray-500 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2">카테고리 추가하기</button>
        </div>

        <!-- Modal -->
        <c:forEach var="category" items="${requestScope.categories}">
            <div
                    data-te-modal-init
                    class="absolute fixed left-0 top-0 z-[1055] hidden h-full w-full overflow-y-auto overflow-x-hidden outline-none"
                    id="category-${category.categoryID}"
                    tabindex="-1"
                    aria-labelledby="exampleModalCenterTitle"
                    aria-modal="true"
                    role="dialog">
                <div
                        data-te-modal-dialog-ref
                        class="pointer-events-none relative flex min-h-[calc(100%-1rem)] w-auto translate-y-[-50px] items-center opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:min-h-[calc(100%-3.5rem)] min-[576px]:max-w-[500px]">
                    <div
                            class="pointer-events-auto relative flex w-full flex-col rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600">
                        <div
                                class="flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50">
                            <!--Modal title-->
                            <h5
                                    class="text-xl font-medium leading-normal text-neutral-800 dark:text-neutral-200"
                                    id="exampleModalCenterTitle">
                                카테고리 이름 수정
                            </h5>
                            <!--Close button-->
                            <button
                                    type="button"
                                    class="box-content rounded-none border-none hover:no-underline hover:opacity-75 focus:opacity-100 focus:shadow-none focus:outline-none"
                                    data-te-modal-dismiss
                                    aria-label="Close">
                                <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        fill="none"
                                        viewBox="0 0 24 24"
                                        stroke-width="1.5"
                                        stroke="currentColor"
                                        class="h-6 w-6">
                                    <path
                                            stroke-linecap="round"
                                            stroke-linejoin="round"
                                            d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>

                        <!--Modal body-->
                        <div class="relative flex-auto p-4" data-te-modal-body-ref>
                            <form action="/admin/category_update.do?category_id=${category.categoryID}" method="POST" class="w-full max-w-lg">
                                <div class="container flex justify-center items-center">
                                    <div class="rounded-lg border relative">
                                        <input type="text" name="category_name" class="h-14 w-96 pl-4 pr-20 rounded-lg z-0 focus:shadow focus:outline-none" value="${category.categoryName}" placeholder="Enter Category Name...">
                                        <div class="absolute top-2 right-2">
                                            <button class="h-10 w-20 text-white rounded-lg bg-teal-700 hover:bg-teal-600">수정</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <!-- 카테고리 추가 모달 -->
        <div
                data-te-modal-init
                class="absolute fixed left-0 top-0 z-[1055] hidden h-full w-full overflow-y-auto overflow-x-hidden outline-none"
                id="category-create"
                tabindex="-1"
                aria-labelledby="exampleModalCenterTitle"
                aria-modal="true"
                role="dialog">
            <div
                    data-te-modal-dialog-ref
                    class="pointer-events-none relative flex min-h-[calc(100%-1rem)] w-auto translate-y-[-50px] items-center opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:min-h-[calc(100%-3.5rem)] min-[576px]:max-w-[500px]">
                <div
                        class="pointer-events-auto relative flex w-full flex-col rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600">
                    <div
                            class="flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50">
                        <!--Modal title-->
                        <h5
                                class="text-xl font-medium leading-normal text-neutral-800 dark:text-neutral-200" >
                            카테고리 추가
                        </h5>
                        <!--Close button-->
                        <button
                                type="button"
                                class="box-content rounded-none border-none hover:no-underline hover:opacity-75 focus:opacity-100 focus:shadow-none focus:outline-none"
                                data-te-modal-dismiss
                                aria-label="Close">
                            <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    stroke-width="1.5"
                                    stroke="currentColor"
                                    class="h-6 w-6">
                                <path
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        </button>
                    </div>

                    <!--Modal body-->
                    <div class="relative flex-auto p-4" data-te-modal-body-ref>
                        <form action="/admin/category_create.do" method="POST" class="w-full max-w-lg">
                            <div class="container flex justify-center items-center">
                                <div class="rounded-lg border relative">
                                    <input type="text" name="category_name" class="h-14 w-96 pl-4 pr-20 rounded-lg z-0 focus:shadow focus:outline-none"  placeholder="Enter Category Name...">
                                    <div class="absolute top-2 right-2">
                                        <button class="h-10 w-20 text-white rounded-lg bg-teal-700 hover:bg-teal-600">추가</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </main>
</div>
</body>
</html>
