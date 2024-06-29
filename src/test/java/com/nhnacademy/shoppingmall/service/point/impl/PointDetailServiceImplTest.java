package com.nhnacademy.shoppingmall.service.point.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.repository.point.PointDetailRepository;
import com.nhnacademy.shoppingmall.service.point.PointDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

class PointDetailServiceImplTest {
    PointDetailRepository pointDetailRepository = Mockito.mock(PointDetailRepository.class);
    PointDetailService pointDetailService = new PointDetailServiceImpl(pointDetailRepository);

    @Test
    @DisplayName("save point detail")
    void save_point_detail() {
        Mockito.when(pointDetailRepository.savePointDetail(any())).thenReturn(1);
        pointDetailService.savePointDetail(PointDetail.builder().build());
        Mockito.verify(pointDetailRepository, Mockito.times(1)).savePointDetail(any());
    }

    @Test
    @DisplayName("pagination")
    void pagination() {
        List<PointDetail> pointDetails = new ArrayList<>();
        Mockito.when(pointDetailRepository.getPointDetailPageByUserID(anyString(), anyInt(), anyInt())).thenReturn(new Page<>(pointDetails, 0));
        pointDetailService.getPointDetailPageByUserID("any", 1, 10);
        Mockito.verify(pointDetailRepository, Mockito.times(1)).getPointDetailPageByUserID(anyString(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("get current point")
    void get_current_point() {
        Mockito.when(pointDetailRepository.getCurrentPointAmountByUserID(anyString())).thenReturn(0);
        pointDetailService.getCurrentPointByUserID("any");
        Mockito.verify(pointDetailRepository, Mockito.times(1)).getCurrentPointAmountByUserID(anyString());
    }
}