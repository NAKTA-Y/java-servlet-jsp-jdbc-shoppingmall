<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/20/24
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html lang="kor">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/output.css" />
    <title></title>
</head>
<body>
<!-- Contents -->
<div class="container mx-auto font-kumbh text-base">
    <!-- Main Content -->
    <main class="w-full flex flex-col lg:flex-row">
        <!-- Gallery -->
        <section
                class="h-fit flex-col gap-8 mt-16 sm:flex sm:flex-row sm:gap-4 sm:h-full sm:mt-24 sm:mx-2 md:gap-8 md:mx-4 lg:flex-col lg:mx-0 lg:mt-36"
        >
            <c:if test="${not empty requestScope.productImages}">
                <picture
                        class="mt-12 relative flex items-center sm:bg-transparent"
                >
                    <img
                            src="${requestScope.productImages[0].productImage}"
                            alt="sneaker"
                            class="block sm:rounded-xl xl:w-[70%] xl:rounded-xl m-auto pointer-events-none transition duration-300 lg:w-3/4 lg:pointer-events-auto lg:cursor-pointer lg:hover:shadow-xl"
                            id="hero"
                    />
                </picture>

                <div
                        class="thumbnails hidden justify-between gap-4 m-auto sm:flex sm:flex-col sm:justify-start sm:items-center sm:h-fit md:gap-5 lg:flex-row"
                >
                    <div
                            id="1"
                            class="w-1/5 cursor-pointer rounded-xl sm:w-28 md:w-32 lg:w-[72px] xl:w-[78px] ring-active"
                    >
                        <img

                                src="${requestScope.productImages[0].productImage}"
                                alt="thumbnail"
                                class="rounded-xl hover:opacity-50 transition active"
                                id="thumb-1"
                        />
                    </div>

                    <c:forEach var="image" items="${requestScope.productImages}" begin="1" varStatus="status">
                        <div
                                id="${status.index + 1}"
                                class="w-1/5 cursor-pointer rounded-xl sm:w-28 md:w-32 lg:w-[72px] xl:w-[78px]"
                        >
                            <img
                                    src="${image.productImage}"
                                    alt="thumbnail"
                                    class="rounded-xl hover:opacity-50 transition"
                                    id="thumb-${status.index + 1}"
                            />
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </section>

        <!-- Text -->
        <section
                class="w-full p-6 lg:mt-36 lg:pr-20 lg:py-10 2xl:pr-40 2xl:mt-40"
        >
            <h4
                    class="font-bold text-teal-700 mb-2 uppercase text-xs tracking-widest"
            >
                ${requestScope.product.brand}
            </h4>
            <h1
                    class="text-very-dark mb-4 font-bold text-3xl lg:text-4xl"
            >
                ${requestScope.product.modelName}
            </h1>
            <p class="text-dark-grayish mb-6 text-base sm:text-lg">
                ${requestScope.product.description}
            </p>

            <div
                    class="flex items-center justify-between mb-6 sm:flex-col sm:items-start"
            >
                <div class="flex items-center gap-4">
                    <h3
                            class="text-very-dark font-bold text-3xl inline-block"
                    >
                        <fmt:formatNumber value="${requestScope.product.sale}"/>₩
                    </h3>
                    <span
                            class="inline-block h-fit py-0.5 px-2 font-bold text-red-700 rounded-lg text-sm"
                    >${requestScope.product.discountRate}%</span
                    >
                </div>
                <p
                        class="text-dark-grayish w-fit line-through decoration-dark-grayish decoration-1 my-auto"
                >
                    <fmt:formatNumber value="${requestScope.product.price}"/>₩
                </p>
                <p
                        class="text-dark-grayish w-fit my-auto"
                >
                    재고 수량 : ${requestScope.product.stock}
                </p>
            </div>

            <form action="/cart/add.do" method="POST" class="flex flex-col gap-5 mb-16 sm:flex-row lg:mb-0">
                <input type="hidden" name="product_id" value="${requestScope.product.productID}">
                <div
                        class="w-full h-10 text-sm bg-light py-2 flex items-center justify-between rounded-lg font-bold relatives sm:w-80"
                >
                    <div onclick="changeAmount(-1, ${requestScope.product.stock})" id="minus" class="plus-minus">
                        <svg
                                width="12"
                                height="4"
                                xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink"
                        >
                            <defs>
                                <path
                                        d="M11.357 3.332A.641.641 0 0 0 12 2.69V.643A.641.641 0 0 0 11.357 0H.643A.641.641 0 0 0 0 .643v2.046c0 .357.287.643.643.643h10.714Z"
                                        id="a"
                                />
                            </defs>
                            <use
                                    fill="#0f766e"
                                    fill-rule="nonzero"
                                    xlink:href="#a"
                            />
                        </svg>
                    </div>
                    <input type="hidden" name="amount" id="amount" value="1">
                    <span id="amount_display" class="select-none">1</span>
                    <div onclick="changeAmount(1, ${requestScope.product.stock})" id="plus" class="plus-minus">
                        <svg
                                width="12"
                                height="12"
                                xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink"
                                id="plus"
                        >
                            <defs>
                                <path
                                        d="M12 7.023V4.977a.641.641 0 0 0-.643-.643h-3.69V.643A.641.641 0 0 0 7.022 0H4.977a.641.641 0 0 0-.643.643v3.69H.643A.641.641 0 0 0 0 4.978v2.046c0 .356.287.643.643.643h3.69v3.691c0 .356.288.643.644.643h2.046a.641.641 0 0 0 .643-.643v-3.69h3.691A.641.641 0 0 0 12 7.022Z"
                                        id="b"
                                />
                            </defs>
                            <use
                                    fill="#0f766e"
                                    fill-rule="nonzero"
                                    xlink:href="#b"
                                    id="plus"
                            />
                        </svg>
                    </div>
                </div>
                <button
                        class="w-full h-10 bg-teal-700 py-2 flex items-center justify-center gap-4 text-xs rounded-lg font-bold text-light shadow-md hover:brightness-125 transition select-none"
                        id="add-cart"
                >
                    <svg
                            width="18"
                            height="18"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 22 20"
                    >
                        <path
                                d="M20.925 3.641H3.863L3.61.816A.896.896 0 0 0 2.717 0H.897a.896.896 0 1 0 0 1.792h1l1.031 11.483c.073.828.52 1.726 1.291 2.336C2.83 17.385 4.099 20 6.359 20c1.875 0 3.197-1.87 2.554-3.642h4.905c-.642 1.77.677 3.642 2.555 3.642a2.72 2.72 0 0 0 2.717-2.717 2.72 2.72 0 0 0-2.717-2.717H6.365c-.681 0-1.274-.41-1.53-1.009l14.321-.842a.896.896 0 0 0 .817-.677l1.821-7.283a.897.897 0 0 0-.87-1.114ZM6.358 18.208a.926.926 0 0 1 0-1.85.926.926 0 0 1 0 1.85Zm10.015 0a.926.926 0 0 1 0-1.85.926.926 0 0 1 0 1.85Zm2.021-7.243-13.8.81-.57-6.341h15.753l-1.383 5.53Z"
                                fill="hsl(223, 64%, 98%)"
                                fill-rule="nonzero"
                        />
                    </svg>
                    <p class="text-sm mt-1">장바구니 담기</p>
                </button>
            </form>
        </section>
    </main>
</div>

<!-- pagination -->
<div class="mx-60">
<div class="mt-52 mb-16">
    <h2 class="text-3xl font-bold tracking-tight text-gray-900 text-center">상품 리뷰</h2>
</div>
    <ul class="">
        <c:forEach var="review" items="${requestScope.reviews}">
            <li class="py-8 text-left border px-4 m-2 rounded">
                <div class="flex items-start">
                    <img class="block h-10 w-10 max-w-full flex-shrink-0 rounded-full align-middle" src="${pageContext.request.contextPath}/resources/icon/user/user.png" alt="" />
                    <div class="ml-6">
                        <div class="flex items-center">
                            <c:forEach var="i" begin="1" end="${review.rating}">
                                <svg class="block h-6 w-6 align-middle text-yellow-500" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" class=""></path>
                                </svg>
                            </c:forEach>
                            <c:forEach var="i" begin="${review.rating}" end="4">
                                <svg class="block h-6 w-6 align-middle text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" class=""></path>
                                </svg>
                            </c:forEach>
                        </div>
                        <p class="mt-5 text-base text-gray-900">${review.comments}</p>
                        <p class="mt-5 text-sm font-bold text-gray-900">${requestScope.userMap[review.userID].userName}</p>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>

    <div class="mt-16 w-full flex items-center justify-evenly border-t border-gray-200 mb-20">
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
                <a href="/product/detail.do?product_id=${product.productID}&page=${requestScope.startPage - 10}&pageSize=${requestScope.pageSize}">
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
                        <a href="/product/detail.do?product_id=${product.productID}&page=${page}&pageSize=${requestScope.pageSize}"
                           class="text-sl font-medium leading-none cursor-pointer text-gray-600 hover:text-teal-700 border-t border-transparent hover:border-teal-600 pt-3 mr-4 px-2">${page}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <c:choose>
            <c:when test="${requestScope.lastPage < requestScope.totalPage}">
                <a href="/product/detail.do?product_id=${product.productID}&page=${requestScope.lastPage + 1}&pageSize=${requestScope.pageSize}">
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

<!-- submit review -->
<div class="mx-auto p-8 bg-white shadow-lg rounded lg:mt-16 lg:mb-16">
    <h1 class="text-xl font-bold mb-4">리뷰를 남겨주세요</h1>

    <form id="reviewForm" action="/reviewAction.do", method="POST">
        <div class="mt-5">
            <textarea id="comments" style="resize: none" name="comments" rows="4" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required></textarea>
        </div>
        <input type="hidden" name="product_id" value="${requestScope.product.productID}"/>
        <input type="hidden" id="rating" name="rating" value="0">
        <div class="mb-4 flex justify-between items-center">
            <ul
                    id="stars"
                    class="my-1 flex list-none gap-1 p-0"
                    data-te-rating-init>
                <li>
    <span
            class="text-yellow-300 [&>svg]:h-5 [&>svg]:w-5"
            data-te-rating-icon-ref>
      <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor">
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
      </svg>
    </span>
                </li>
                <li>
    <span
            class="text-yellow-300 [&>svg]:h-5 [&>svg]:w-5"
            data-te-rating-icon-ref>
      <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor">
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
      </svg>
    </span>
                </li>
                <li>
    <span
            class="text-yellow-300 [&>svg]:h-5 [&>svg]:w-5"
            data-te-rating-icon-ref>
      <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor">
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
      </svg>
    </span>
                </li>
                <li>
    <span
            class="text-yellow-300 [&>svg]:h-5 [&>svg]:w-5"
            data-te-rating-icon-ref>
      <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor">
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
      </svg>
    </span>
                </li>
                <li>
    <span class="text-yellow-300 [&>svg]:h-5 [&>svg]:w-5" data-te-rating-icon-ref>
      <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor">
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
      </svg>
    </span>
                </li>
            </ul>

            <button class="mt-2 rounded relative inline-flex group items-center justify-center px-3.5 py-2 m-1 cursor-pointer border-b-4 border-l-2 active:border-teal-700 active:shadow-none shadow-lg bg-gradient-to-tr from-teal-700 to-teal-600 border-teal-700 text-white">
                <span class="absolute w-0 h-0 transition-all duration-300 ease-out bg-white rounded-full group-hover:w-32 group-hover:h-32 opacity-10"></span>
                <span class="relative">리뷰등록</span>
            </button>
        </div>
    </form>
</div>
</div>

<script src="/js/script.js"></script>
<script>
    // JavaScript to handle star rating
    const stars = document.querySelectorAll('#stars li');
    const ratingInput = document.getElementById('rating');

    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            ratingInput.value = index + 1; // Update the rating value
        });
    });

    function changeAmount(delta, stock) {
        let amount_display = document.getElementById('amount_display');
        let amount = document.getElementById('amount');
        let value = parseInt(amount_display.innerText);

        let newValue = value + delta;
        if (newValue < 1) {
            newValue = 1;
        }

        if (newValue > stock) {
            newValue = stock;
        }

        amount_display.innerText = newValue;
        amount.value = newValue;
    }
</script>
</body>
</html>