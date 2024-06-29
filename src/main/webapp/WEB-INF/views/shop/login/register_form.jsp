<%--
  Created by IntelliJ IDEA.
  User: nakta
  Date: 1/21/24
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div
        class="min-h-screen flex flex-col justify-center bg-gray-100"
>
    <div
            class="
          flex flex-col
          bg-white
          shadow-md
          px-4
          sm:px-20
          md:px-24
          lg:px-26
          py-8
          rounded-3xl
          w-50
          max-w-md
          self-center
          mt-28
          mb-28
        "
    >
        <div class="font-medium self-center text-xl sm:text-3xl text-gray-800">
            회원가입
        </div>

        <div class="mt-10 items-left">
            <form action="/registerAction.do", method="POST">
                <div class="flex flex-col mb-5">
                    <label
                            for="name"
                            class="mb-1 text-xs tracking-wide text-gray-600"
                    >이름:</label
                    >
                    <div class="relative">
                        <div
                                class="
                    inline-flex
                    items-center
                    justify-center
                    absolute
                    left-0
                    top-0
                    h-full
                    w-10
                    text-gray-400
                  "
                        >
                            <i class="fas fa-user text-blue-500"></i>
                        </div>

                        <input
                                id="name"
                                type="text"
                                name="name"
                                class="
                    text-sm
                    placeholder-gray-500
                    pl-10
                    pr-4
                    rounded-2xl
                    border border-gray-400
                    w-full
                    py-2
                    focus:outline-none focus:border-blue-400
                  "
                                placeholder="Enter your name"
                                required
                        />
                    </div>
                </div>
                <div class="flex flex-col mb-5">
                    <label
                            for="id"
                            class="mb-1 text-xs tracking-wide text-gray-600"
                    >ID:</label
                    >
                    <div class="relative">
                        <div
                                class="
                    inline-flex
                    items-center
                    justify-center
                    absolute
                    left-0
                    top-0
                    h-full
                    w-10
                    text-gray-400
                  "
                        >
                            <i class="fas fa-at text-blue-500"></i>
                        </div>

                        <input
                                id="id"
                                type="text"
                                name="id"
                                class="
                    text-sm
                    placeholder-gray-500
                    pl-10
                    pr-4
                    rounded-2xl
                    border border-gray-400
                    w-full
                    py-2
                    focus:outline-none focus:border-blue-400
                  "
                                placeholder="Enter your email"
                                required
                        />
                    </div>
                </div>
                <div class="flex flex-col mb-6">
                    <label
                            for="password"
                            class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
                    >Password:</label
                    >
                    <div class="relative">
                        <div
                                class="
                    inline-flex
                    items-center
                    justify-center
                    absolute
                    left-0
                    top-0
                    h-full
                    w-10
                    text-gray-400
                  "
                        >
                  <span>
                    <i class="fas fa-lock text-blue-500"></i>
                  </span>
                        </div>

                        <input
                                id="password"
                                type="password"
                                name="password"
                                class="
                    text-sm
                    placeholder-gray-500
                    pl-10
                    pr-4
                    rounded-2xl
                    border border-gray-400
                    w-full
                    py-2
                    focus:outline-none focus:border-blue-400
                  "
                                placeholder="Enter your password"
                                required
                        />
                    </div>
                </div>

                <div class="flex flex-col mb-6">
                    <label
                            for="password"
                            class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
                    >생년월일:</label
                    >
                    <div class="relative">
                        <div
                                class="
                    inline-flex
                    items-center
                    justify-center
                    absolute
                    left-0
                    top-0
                    h-full
                    w-10
                    text-gray-400
                  "
                        >
                  <span>
                    <i class="fa-regular fa-calendar-days text-blue-500"></i>
                  </span>
                        </div>

                        <input
                                id="birth"
                                type="text"
                                name="birth"
                                class="
                    text-sm
                    placeholder-gray-500
                    pl-10
                    pr-4
                    rounded-2xl
                    border border-gray-400
                    w-full
                    py-2
                    focus:outline-none focus:border-blue-400
                  "
                                placeholder="ex: 19990101"
                                required
                        />
                    </div>
                </div>

                <div class="flex flex-col mb-6">
                    <label
                            for="password"
                            class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
                    >연락처:</label
                    >
                    <div class="relative">
                        <div
                                class="
                    inline-flex
                    items-center
                    justify-center
                    absolute
                    left-0
                    top-0
                    h-full
                    w-10
                    text-gray-400
                  "
                        >
                  <span>
                    <i class="fa-solid fa-phone text-blue-500"></i>
                  </span>
                        </div>

                        <input
                                id="phone"
                                type="tel"
                                name="phone"
                                class="
                    text-sm
                    placeholder-gray-500
                    pl-10
                    pr-4
                    rounded-2xl
                    border border-gray-400
                    w-full
                    py-2
                    focus:outline-none focus:border-blue-400
                  "
                                placeholder="ex: 010-1234-1234"
                                required
                        />
                    </div>
                </div>

                <div class="flex w-full">
                    <button
                            type="submit"
                            class="
                  flex
                  mt-2
                  items-center
                  justify-center
                  focus:outline-none
                  text-white text-sm
                  sm:text-base
                  bg-blue-500
                  hover:bg-blue-600
                  rounded-2xl
                  py-2
                  w-full
                  transition
                  duration-150
                  ease-in
                "
                    >
                        <span class="mr-2 uppercase">Sign Up</span>
                        <span>
                  <svg
                          class="h-6 w-6"
                          fill="none"
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                  >
                    <path
                            d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                </span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
