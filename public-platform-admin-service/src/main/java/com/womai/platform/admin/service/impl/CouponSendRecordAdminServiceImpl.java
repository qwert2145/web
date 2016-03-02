package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.CouponSendRecordAdminService;
import com.womai.platform.api.model.CouponSendRecordApi;
import com.womai.platform.api.service.CouponSendRecordService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public class CouponSendRecordAdminServiceImpl implements CouponSendRecordAdminService {
    private CouponSendRecordService couponSendRecordSoaService;

    public CouponSendRecordService getCouponSendRecordSoaService() {
        return couponSendRecordSoaService;
    }

    public void setCouponSendRecordSoaService(CouponSendRecordService couponSendRecordSoaService) {
        this.couponSendRecordSoaService = couponSendRecordSoaService;
    }

    @Override
    public void deleteById(int id) {
        couponSendRecordSoaService.deleteById(id);
    }

    @Override
    public int insertCouponSendRecord(CouponSendRecordApi record) {
        couponSendRecordSoaService.insertCouponSendRecord(record);
        return 0;
    }

    @Override
    public CouponSendRecordApi selectById(int id) {
        return couponSendRecordSoaService.selectById(id);
    }

    @Override
    public int updateCouponSendRecord(CouponSendRecordApi record) {
        couponSendRecordSoaService.updateCouponSendRecord(record);
        return 0;
    }

    @Override
    public List<CouponSendRecordApi> selectListByPage(HashMap<String, Object> offsetLimit) {
        return couponSendRecordSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return couponSendRecordSoaService.getCount();
    }

    @Override
    public List<CouponSendRecordApi> selectByOpenId(Integer couponActiveId,String openid) {
        return couponSendRecordSoaService.selectByOpenId(couponActiveId,openid);
    }

    @Override
    public List<CouponSendRecordApi> selectByActiveId(Integer couponActiveId) {
        return couponSendRecordSoaService.selectByActiveId(couponActiveId);
    }

    @Override
    public int getCountByOpenId(Integer couponActiveId,String openid) {
        return couponSendRecordSoaService.getCountByOpenId(couponActiveId, openid);
    }
}
