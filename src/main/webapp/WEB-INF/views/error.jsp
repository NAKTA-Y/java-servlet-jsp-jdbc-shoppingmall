<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/28/24
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tw-elements/dist/css/tw-elements.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
</head>
<body>
<main class="grid min-h-full place-items-center bg-white px-6 py-24 sm:py-32 lg:px-8">
    <div class="text-center">
        <c:choose>
            <c:when test="${empty requestScope.status_code}">
                <p class="text-base text-xl font-semibold text-indigo-600">404</p>
            </c:when>
            <c:otherwise>
                <p class="text-base text-xl font-semibold text-indigo-600">${requestScope.status_code}</p>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${empty requestScope.error_message}">
                <h1 class="mt-4 text-2xl font-bold tracking-tight text-gray-900 sm:text-5xl">콘텐츠를 찾을 수 없습니다.</h1>
            </c:when>
            <c:otherwise>
                <h1 class="mt-4 text-2xl font-bold tracking-tight text-gray-900 sm:text-5xl">${requestScope.error_message}</h1>
            </c:otherwise>
        </c:choose>

        <div class="mt-10 flex items-center justify-center gap-x-6">
            <a href="/index.do" class="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Go back home</a>
        </div>
    </div>
</main>
</body>
</html>
