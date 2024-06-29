package com.nhnacademy.shoppingmall.controller.point;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.Pagination;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.point.impl.PointDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.service.point.PointDetailService;
import com.nhnacademy.shoppingmall.service.point.impl.PointDetailServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/mypage/point_list.do", method = RequestMapping.Method.GET)
public class PointListController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final PointDetailService pointDetailService = new PointDetailServiceImpl(new PointDetailRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String pageParam = req.getParameter("page");
        String pageSizeParam = req.getParameter("pageSize");

        try {
            int page = Objects.isNull(pageParam) || pageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(pageParam);
            int pageSize = Objects.isNull(pageSizeParam) || pageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeParam);

            Page<PointDetail> pointDetailPage = pointDetailService.getPointDetailPageByUserID(user.getUniqueUserID(), page, pageSize);
            List<PointDetail> pointDetails = pointDetailPage.getContent();

            req.setAttribute("pointDetails", pointDetails);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("totalPage", Pagination.getTotalPage(pointDetailPage.getTotalCount(), pageSize));
            req.setAttribute("startPage", Pagination.getStartPage(page));
            req.setAttribute("lastPage", Pagination.getLastPage(pointDetailPage.getTotalCount(), page, pageSize));

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/mypage/point_list";
    }
}
