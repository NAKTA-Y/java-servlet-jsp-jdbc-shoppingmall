package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.entity.user.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(
        filterName = "loginCheckFilter",
        urlPatterns = {
                "/mypage/*",
                "/order/*",
                "/cart/*",
                "/reviewAction.do",
                "/logout.do"
        },
        initParams = {
                @WebInitParam(name = "redirect", value = "/login.do")
        }
)
public class LoginCheckFilter extends HttpFilter {
    private String loginURI;

    @Override
    public void init(FilterConfig config) throws ServletException {
        loginURI = config.getInitParameter("redirect");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.sendRedirect(loginURI);
            return;
        }

        chain.doFilter(req, res);
    }
}