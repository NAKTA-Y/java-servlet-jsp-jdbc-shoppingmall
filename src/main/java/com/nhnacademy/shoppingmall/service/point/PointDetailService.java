package com.nhnacademy.shoppingmall.service.point;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;

public interface PointDetailService {
    int savePointDetail(PointDetail pointDetail);
    Page<PointDetail> getPointDetailPageByUserID(String uniqueUserID, int page, int pageSize);
    int getCurrentPointByUserID(String uniqueUserID);
}
