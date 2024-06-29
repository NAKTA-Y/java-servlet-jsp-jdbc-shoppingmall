<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div
        class="min-h-screen flex flex-col items-center justify-center bg-gray-100"
>
    <div
            class="
          flex flex-col
          bg-white
          shadow-md
          px-4
          sm:px-6
          md:px-8
          lg:px-10
          py-8
          rounded-3xl
          w-50
          max-w-md
        "
    >
        <div class="font-medium self-center text-xl sm:text-3xl text-gray-800">
            회원 로그인
        </div>

        <div class="mt-10">
            <form action="/loginAction.do" method="POST">
                <div class="flex flex-col mb-5">
                    <label
                            for="user_id"
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
                                id="user_id"
                                type="text"
                                name="user_id"
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
                                placeholder="Enter your ID"
                        />
                    </div>
                </div>
                <div class="flex flex-col mb-6">
                    <label
                            for="user_password"
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
                                id="user_password"
                                type="password"
                                name="user_password"
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
                        <span class="mr-2 uppercase">Sign In</span>
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
    <div class="flex justify-center items-center mt-6">
        <a
                href="#"
                target="_blank"
                class="
            inline-flex
            items-center
            text-gray-700
            font-medium
            text-xs text-center
          "
        >
          <span class="ml-2"
          >아직 계정이 없으신가요?
            <a
                    href="/register.do"
                    class="text-xs ml-2 text-blue-500 font-semibold"
            >회원가입</a
            ></span
          >
        </a>
    </div>
</div>
</body>
</html>
