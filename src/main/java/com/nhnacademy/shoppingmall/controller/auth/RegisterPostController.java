package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequestMapping(value = "/registerAction.do", method = RequestMapping.Method.POST)
public class RegisterPostController implements BaseController {
    private static final int WELCOME_POINT = 1_000_000;
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String birth = req.getParameter("birth");
        String phone = req.getParameter("phone");

        if (ObjectUtils.anyNull(name, id, password, birth, phone)) {
            resp.setStatus(400);
            throw new RuntimeException("BAD REQUEST");
        }

        if (userService.isExistUserByID(id)) {
            resp.setStatus(400);
            throw new RuntimeException("Already Exist User ID");
        }

        String uniqueUserID = UUID.randomUUID().toString();
        User newUser = User.builder()
                .uniqueUserID(uniqueUserID)
                .userName(name)
                .userID(id)
                .userPassword(password)
                .userBirth(birth)
                .userTelephone(phone)
                .createdAt(LocalDateTime.now())
                .latestLoginAt(LocalDateTime.now())
                .userAuth(User.Auth.ROLE_USER)
                .state(User.State.ACTIVE)
                .build();

        userService.saveUser(newUser);

        try {
            RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
            requestChannel.addRequest(new PointChannelRequest(uniqueUserID, WELCOME_POINT));
        } catch (InterruptedException e) {
            log.error("포인트 적립 예외 발생: " + e.getMessage());
        }

        session.setAttribute("user", newUser);

        return "redirect:/";
    }
}
