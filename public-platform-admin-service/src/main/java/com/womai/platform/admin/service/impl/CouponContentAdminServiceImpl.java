package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.CouponContentAdminService;
import com.womai.platform.api.model.CouponContentApi;
import com.womai.platform.api.service.CouponContentService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public class CouponContentAdminServiceImpl implements CouponContentAdminService {
    private CouponContentService couponContentSoaService;

    public CouponContentService getCouponContentSoaService() {
        return couponContentSoaService;
    }

    public void setCouponContentSoaService(CouponContentService couponContentSoaService) {
        this.couponContentSoaService = couponContentSoaService;
    }

    @Override
    public void deleteById(int id) {
        couponContentSoaService.deleteById(id);
    }

    @Override
    public int insertCouponContent(CouponContentApi record) {
        couponContentSoaService.insertCouponContent(record);
        return 0;
    }

    @Override
    public CouponContentApi selectById(int id) {
        return couponContentSoaService.selectById(id);
    }

    @Override
    public int updateCouponContent(CouponContentApi record) {
        couponContentSoaService.updateCouponContent(record);
        return 0;
    }

    @Override
    public List<CouponContentApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return couponContentSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return couponContentSoaService.getCount();
    }

    @Override
    public int insertBatch(List<CouponContentApi> couponContentApis) {
        couponContentSoaService.insertBatch(couponContentApis);
        return 0;
    }
}
