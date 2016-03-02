package com.womai.platform.admin.service.inte;

import com.womai.platform.api.model.CouponActiveApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public interface CouponActiveAdminService {

    void deleteById(int id);
    int insertCouponActive(CouponActiveApi record);
    CouponActiveApi selectById(int id);
    int updateCouponActive(CouponActiveApi record);
    List<CouponActiveApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();
    List<CouponActiveApi> selectByAccountId(Integer accountId);
    List<CouponActiveApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);

    List<CouponActiveApi> selectByScenceId(String qrCode,int accountId);
    List<CouponActiveApi> selectByKeyWord(String keyWord,int accountId);
}
