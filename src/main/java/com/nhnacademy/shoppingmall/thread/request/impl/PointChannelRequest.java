package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.repository.point.impl.PointDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.service.point.PointDetailService;
import com.nhnacademy.shoppingmall.service.point.impl.PointDetailServiceImpl;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final PointDetailService pointDetailService = new PointDetailServiceImpl(new PointDetailRepositoryImpl());
    private final String uniqueUserID;
    private final int volume;

    public PointChannelRequest(String uniqueUserID, int volume) {
        this.uniqueUserID = uniqueUserID;
        this.volume = volume;
    }

    /**
     * TODO: 포인트 적립 내역 테이블 추가 및 로직 추가
     */
    @Override
    public void execute() {
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        try {
            DbConnectionThreadLocal.initialize();
            log.debug("pointChannel execute");

            PointDetail savePointDetail = PointDetail.builder()
                    .volume(volume)
                    .type(PointDetail.Type.POINT_SAVE)
                    .createdAt(LocalDateTime.now())
                    .uniqueUserID(uniqueUserID)
                    .build();

            pointDetailService.savePointDetail(savePointDetail);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            DbConnectionThreadLocal.setSqlError(true);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }
}
