package com.nhnacademy.shoppingmall.repository.point;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;

public interface PointDetailRepository {
    int savePointDetail(PointDetail pointDetail);
    Page<PointDetail> getPointDetailPageByUserID(String uniqueUserID, int page, int pageSize);
    int getCurrentPointAmountByUserID(String uniqueUserID);
}
