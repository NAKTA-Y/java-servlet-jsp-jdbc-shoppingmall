package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.Pagination;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.service.order.OrderService;
import com.nhnacademy.shoppingmall.service.order.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/mypage/order_list.do", method = RequestMapping.Method.GET)
public class OrderListController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String pageParam = req.getParameter("page");
        String pageSizeParam = req.getParameter("pageSize");

        try {
            int page = Objects.isNull(pageParam) || pageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(pageParam);
            int pageSize = Objects.isNull(pageSizeParam) || pageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeParam);

            Page<Order> orderPage = orderService.getOrderPageByUserID(user.getUniqueUserID(), page, pageSize);
            List<Order> orders = orderPage.getContent();

            req.setAttribute("orders", orders);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("totalPage", Pagination.getTotalPage(orderPage.getTotalCount(), pageSize));
            req.setAttribute("startPage", Pagination.getStartPage(page));
            req.setAttribute("lastPage", Pagination.getLastPage(orderPage.getTotalCount(), page, pageSize));

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/mypage/order_list";
    }
}
