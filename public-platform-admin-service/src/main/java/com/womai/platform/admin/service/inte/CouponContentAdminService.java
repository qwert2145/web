package com.womai.platform.admin.service.inte;

import com.womai.platform.api.model.CouponContentApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public interface CouponContentAdminService {
    void deleteById(int id);
    int insertCouponContent(CouponContentApi record);
    CouponContentApi selectById(int id);
    int updateCouponContent(CouponContentApi record);
    List<CouponContentApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();
    int insertBatch(List<CouponContentApi> couponContentApis);
}
