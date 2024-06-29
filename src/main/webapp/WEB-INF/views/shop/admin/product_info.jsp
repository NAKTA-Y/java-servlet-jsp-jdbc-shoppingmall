<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/23/24
  Time: 15:21
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

    <div class="mb-6">
      <h2 class="text-2xl font-bold tracking-tight text-gray-900 text-center">이미지 수정</h2>
    </div>

    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
      <c:forEach var="image" items="${requestScope.productImages}">
        <div class="relative mt-6 pt-3">
          <picture class="flex items-center sm:bg-transparent">
            <button
                    class="absolute top-3 right-0 bg-red-500 text-white rounded-full h-8 w-8 flex items-center justify-center cursor-pointer"
                    onclick="removeExistImage(this);"
            >
              X
            </button>
            <img
                    id="${image.productImageID}"
                    src="${image.productImage}"
                    alt="sneaker"
                    class="border block sm:rounded-xl xl:rounded-xl pointer-events-none transition duration-300 lg:pointer-events-auto lg:cursor-pointer lg:hover:shadow-xl"
            />
          </picture>
        </div>
      </c:forEach>
    </div>

    <div class="flex mt-8 justify-end">
      <input type="file" id="imageInput" hidden onchange="addImage(event)" accept="image/*">
      <button id="addImageButton" class="relative inline-block text-lg group">
            <span class="relative z-10 block px-5 py-3 overflow-hidden font-medium leading-tight text-gray-800 transition-colors duration-300 ease-out border-2 border-gray-900 rounded-lg group-hover:text-white">
              <span class="absolute inset-0 w-full h-full px-5 py-3 rounded-lg bg-gray-50"></span>
              <span class="absolute left-0 w-48 h-48 -ml-2 transition-all duration-300 origin-top-right -rotate-90 -translate-x-full translate-y-12 bg-gray-900 group-hover:-rotate-180 ease"></span>
              <span class="relative">이미지 추가</span>
            </span>
        <span class="absolute bottom-0 right-0 w-full h-12 -mb-1 -mr-1 transition-all duration-200 ease-linear bg-gray-900 rounded-lg group-hover:mb-0 group-hover:mr-0" data-rounded="rounded-lg"></span>
      </button>
    </div>

    <div class="mt-52 mb-6">
      <h2 class="text-2xl font-bold tracking-tight text-gray-900 text-center">상품 정보 수정</h2>
    </div>

    <div class="flex justify-center items-start my-12">
      <!-- Image container -->
      <div class="flex flex-col">
        <!-- Image container -->
        <picture class="relative">
          <img src="${requestScope.product.thumbnail}"
               class="border block sm:rounded-xl xl:w-[70%] xl:rounded-xl m-auto pointer-events-none transition duration-300 lg:w-3/4 lg:pointer-events-auto lg:cursor-pointer lg:hover:shadow-xl"
               id="hero"/>
        </picture>
        <div
                class="mt-6 thumbnails hidden justify-between gap-4 m-auto sm:flex sm:flex-col sm:justify-start sm:items-center sm:h-fit md:gap-5 lg:flex-row"
        >
          <input type="file" id="thumbnailInput" hidden accept="image/*">
          <button class="relative inline-block text-lg group" onclick="openThumbnailUploader()">
    <span class="relative z-10 block px-5 py-3 overflow-hidden font-medium leading-tight text-gray-800 transition-colors duration-300 ease-out border-2 border-gray-900 rounded-lg group-hover:text-white">
      <span class="absolute inset-0 w-full h-full px-5 py-3 rounded-lg bg-gray-50"></span>
      <span class="absolute left-0 w-48 h-48 -ml-2 transition-all duration-300 origin-top-right -rotate-90 -translate-x-full translate-y-12 bg-gray-900 group-hover:-rotate-180 ease"></span>
      <c:choose>
        <c:when test="${empty requestScope.product}">
          <span class="relative">썸네일 추가</span>
        </c:when>
        <c:otherwise>
          <span class="relative">썸네일 수정</span>
        </c:otherwise>
      </c:choose>
    </span>
            <span class="absolute bottom-0 right-0 w-full h-12 -mb-1 -mr-1 transition-all duration-200 ease-linear bg-gray-900 rounded-lg group-hover:mb-0 group-hover:mr-0" data-rounded="rounded-lg"></span>
          </button>
        </div>
      </div>

      <!-- Form container -->
      <form id="updateForm" enctype="multipart/form-data">
      <div class="ml-10">
        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="model_number">
            모델 번호
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="model_number"
                 type="text"
                 name="model_number"
                 value="${requestScope.product.modelNumber}"
                 placeholder="제목">
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="model_name">
            모델 이름
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="model_name"
                 type="text"
                 name="model_name"
                 value="${requestScope.product.modelName}"
                 placeholder="제목">
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="price">
            정가
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="price"
                 type="text"
                 name="price"
                 value="${requestScope.product.price}"
                 placeholder="제목">
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="discount_rate">
            세일 퍼센트
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="discount_rate"
                 type="text"
                 name="discount_rate"
                 value="${requestScope.product.discountRate}"
                 placeholder="제목">
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="stock">
            카테고리
          </label>

          <div class="flex flex-wrap">
            <c:forEach var="category" items="${requestScope.categories}" varStatus="status">
              <div class="w-1/2">
                <div class="flex items-center ps-4 border border-gray-200 rounded">
                  <input id="category_checkbox-${status.index}"
                         type="checkbox"
                         value="${category.categoryID}"
                         name="category_checkbox"
                         class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                         ${fn:contains(requestScope.categoriesByProduct, category) ? 'checked' : ''}>
                  <label for="category_checkbox-${status.index}"
                         class="w-full py-4 ms-2 text-sm font-medium text-gray-900">${category.categoryName}</label>
                </div>
              </div>
            </c:forEach>
          </div>
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="stock">
            재고 수량
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="stock"
                 type="text"
                 name="stock"
                 value="${requestScope.product.stock}"
                 placeholder="제목">
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 text-lg font-bold mb-2" for="brand">
            브랜드
          </label>
          <input required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="brand"
                 type="text"
                 name="brand"
                 value="${requestScope.product.brand}"
                 placeholder="제목">
        </div>

        <div>
          <label class="block text-gray-700 text-lg font-bold mb-2" for="description">
            설명
          </label>
          <textarea required rows="10"
                    class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                    id="description"
                    name="description"
                    placeholder="내용">${requestScope.product.description}</textarea>
        </div>

        <div class="flex mt-6 justify-end">
          <c:choose>
            <c:when test="${empty requestScope.product}">
              <button type="button" onclick="sendFormData('/admin/product_create.do')" class="relative inline-flex items-center justify-center p-4 px-6 py-3 overflow-hidden font-medium text-indigo-600 transition duration-300 ease-out border-2 border-green-700 rounded-full shadow-md group">
          <span class="absolute inset-0 flex items-center justify-center w-full h-full text-white duration-300 -translate-x-full bg-green-700 group-hover:translate-x-0 ease">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path></svg>
          </span>
                <span class="absolute flex items-center justify-center w-full h-full text-green-700 transition-all duration-300 transform group-hover:translate-x-full ease">상품 추가</span>
                <span class="relative invisible">상품 추가</span>
              </button>
            </c:when>
            <c:otherwise>
              <button type="button" onclick="sendFormData('/admin/product_update.do?product_id=${requestScope.product.productID}')" class="relative inline-flex items-center justify-center p-4 px-6 py-3 overflow-hidden font-medium text-indigo-600 transition duration-300 ease-out border-2 border-green-700 rounded-full shadow-md group">
          <span class="absolute inset-0 flex items-center justify-center w-full h-full text-white duration-300 -translate-x-full bg-green-700 group-hover:translate-x-0 ease">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path></svg>
          </span>
                <span class="absolute flex items-center justify-center w-full h-full text-green-700 transition-all duration-300 transform group-hover:translate-x-full ease">상품 수정</span>
                <span class="relative invisible">상품 수정</span>
              </button>

            </c:otherwise>
            </c:choose>
        </div>
      </div>
      </form>
    </div>
  </main>
</div>

<script>
  let deletedImages = []; // 삭제할 이미지의 src 값을 저장할 리스트
  let addedImages = []; // 추가된 이미지 파일을 저장할 리스트
  let selectedThumbnailFile = null; // 썸네일 파일 저장 변수

  document.querySelector("#addImageButton").addEventListener("click", function() {
    document.querySelector("#imageInput").click();
  });

  function addImage(event) {
    const file = event.target.files[0];
    const lastModified = file.lastModified;
    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        const newImageDiv = '<div class="relative mt-6 pt-3">' +
                '<picture class="flex items-center sm:bg-transparent">' +
                '<button class="absolute top-3 right-0 bg-red-500 text-white rounded-full h-8 w-8 flex items-center justify-center cursor-pointer" onclick="removeAddedImage(this);">' +
                'X' +
                '</button>' +
                '<img id="' + lastModified + '" src="' + e.target.result + '" alt="Uploaded Image" class="border block sm:rounded-xl xl:rounded-xl pointer-events-none transition duration-300 lg:pointer-events-auto lg:cursor-pointer lg:hover:shadow-xl" />' +
                '</picture>' +
                '</div>';
        document.querySelector(".grid").innerHTML += newImageDiv;
        addedImages.push(file); // 추가된 이미지 파일을 리스트에 추가
      };
      reader.readAsDataURL(file);
    }

    event.target.value = '';
  }

  function removeExistImage(buttonElement) {
    const imageContainer = buttonElement.parentElement; // 부모 요소를 가져옴
    const imageElement = imageContainer.querySelector("img");

    if (imageElement && imageElement.id) {
      deletedImages.push(imageElement.id); // 삭제할 이미지의 src 값을 리스트에 추가
    }
    buttonElement.parentElement.parentElement.remove();
    console.log(deletedImages);
  }

  // 'X' 버튼을 눌러 이미지 삭제 시 이벤트 핸들러
  function removeAddedImage(buttonElement) {
    const imageContainer = buttonElement.parentElement; // 부모 요소를 가져옴
    const imageElement = imageContainer.querySelector("img");

    if (imageElement && imageElement.id) {
      // 삭제할 이미지의 src 값을 찾아 'addedImages' 리스트에서 해당 이미지를 찾아냅니다.
      const srcToRemove = imageElement.id.toString();
      const indexToRemove = addedImages.findIndex(image => image.lastModified.toString() === srcToRemove);
      if (indexToRemove !== -1) {
        addedImages.splice(indexToRemove, 1);
      }
    }

    // 해당 이미지를 화면에서 제거합니다.
    buttonElement.parentElement.parentElement.remove();
    console.log(addedImages);
  }

  function openThumbnailUploader() {
    document.querySelector("#thumbnailInput").click();
  }

  function showThumbnailPreview(event) {
    const thumbnailPreview = document.querySelector("#hero");
    const file = event.target.files[0];

    if (file) {
      selectedThumbnailFile = file; // 선택한 파일을 전역 변수에 저장
      const reader = new FileReader();
      reader.onload = function(e) {
        thumbnailPreview.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  function updateThumbnail() {
    const thumbnailInput = document.querySelector("#thumbnailInput");
    thumbnailInput.addEventListener("change", showThumbnailPreview);
  }

  function sendFormData(url) {
    const formElement = document.getElementById('updateForm')
    const formData = new FormData(formElement);

    // 추가된 이미지를 FormData에 추가
    addedImages.forEach((file, index) => {
      formData.append('addedImages[' + index + ']', file);
    });

    // 삭제된 이미지를 FormData에 추가
    deletedImages.forEach((id, index) => {
      formData.append('deletedImages[' + index + ']', id);
    });

    // 썸네일 이미지 추가
    if (selectedThumbnailFile) {
      formData.append('thumbnail', selectedThumbnailFile);
    }

    // Fetch API를 사용하여 서버에 비동기 요청 전송
    fetch(url, {
      method: 'POST',
      body: formData
    })
    .then(response => {
      if (response.redirected) {
        window.location.href = response.url;
      } else {
        return response.json();
      }
    });
  }

  updateThumbnail();
</script>
</body>
</html>
