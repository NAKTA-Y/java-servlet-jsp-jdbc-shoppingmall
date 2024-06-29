package com.nhnacademy.shoppingmall.service.point.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.repository.point.PointDetailRepository;
import com.nhnacademy.shoppingmall.service.point.PointDetailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointDetailServiceImpl implements PointDetailService {
    private static final String POINT_DETAIL_SAVE_EXCEPTION_MESSAGE = "포인트 적립 항목을 추가하지 못했습니다.";

    private final PointDetailRepository pointDetailRepository;

    public PointDetailServiceImpl(PointDetailRepository pointDetailRepository) {
        this.pointDetailRepository = pointDetailRepository;
    }

    @Override
    public int savePointDetail(PointDetail pointDetail) {
        int pk = pointDetailRepository.savePointDetail(pointDetail);
        if (pk < 1) log.error(POINT_DETAIL_SAVE_EXCEPTION_MESSAGE);
        return pk;
    }

    @Override
    public Page<PointDetail> getPointDetailPageByUserID(String uniqueUserID, int page, int pageSize) {
        return pointDetailRepository.getPointDetailPageByUserID(uniqueUserID, page, pageSize);
    }

    @Override
    public int getCurrentPointByUserID(String uniqueUserID) {
        return pointDetailRepository.getCurrentPointAmountByUserID(uniqueUserID);
    }
}
