package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.CouponActiveAdminService;
import com.womai.platform.api.model.CouponActiveApi;
import com.womai.platform.api.service.CouponActiveService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public class CouponActiveAdminServiceImpl implements CouponActiveAdminService {
    private CouponActiveService couponActiveSoaService;

    public CouponActiveService getCouponActiveSoaService() {
        return couponActiveSoaService;
    }

    public void setCouponActiveSoaService(CouponActiveService couponActiveSoaService) {
        this.couponActiveSoaService = couponActiveSoaService;
    }

    @Override
    public void deleteById(int id) {
        couponActiveSoaService.deleteById(id);
    }

    @Override
    public int insertCouponActive(CouponActiveApi record) {
        couponActiveSoaService.insertCouponActive(record);
        return 0;
    }

    @Override
    public CouponActiveApi selectById(int id) {
        return couponActiveSoaService.selectById(id);
    }

    @Override
    public int updateCouponActive(CouponActiveApi record) {
        couponActiveSoaService.updateCouponActive(record);
        return 0;
    }

    @Override
    public List<CouponActiveApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return couponActiveSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return couponActiveSoaService.getCount();
    }

    @Override
    public List<CouponActiveApi> selectByAccountId(Integer accountId) {
        return couponActiveSoaService.selectByAccountId(accountId);
    }

    @Override
    public List<CouponActiveApi> query(HashMap<String, Object> queryMap) {
        return couponActiveSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return couponActiveSoaService.queryCount(queryMap);
    }

    @Override
    public List<CouponActiveApi> selectByScenceId(String qrCode, int accountId) {
        return couponActiveSoaService.selectByScenceId(qrCode, accountId);
    }

    @Override
    public List<CouponActiveApi> selectByKeyWord(String keyWord, int accountId) {
        return couponActiveSoaService.selectByKeyWord(keyWord, accountId);
    }
}
