package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {
    private static final int POINT_SAVE_PER_DAYS = 10_000_000;
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final ShoppingCartService shoppingCartService= new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        String id = req.getParameter("user_id");
        String password = req.getParameter("user_password");

        User user = userService.doLogin(id, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        int cartItemCount = shoppingCartService.getCountAllByUserID(user.getUniqueUserID());
        session.setAttribute("cartItemCount", cartItemCount);

        try {
            LocalDate nowDate = LocalDate.from(LocalDateTime.now());
            LocalDate latestLoginDate = LocalDate.from(user.getLatestLoginAt());

            if (nowDate.isAfter(latestLoginDate)) {
                RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
                requestChannel.addRequest(new PointChannelRequest(user.getUniqueUserID(), POINT_SAVE_PER_DAYS));
            }
        } catch (InterruptedException e) {
            log.error("포인트 적립 예외 발생: " + e.getMessage());
        }

        if (Objects.isNull(user)) {
            return "redirect:/login.do";
        }

        return "redirect:/";
    }
}
