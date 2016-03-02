package com.womai.platform.admin.service.inte;

import com.womai.platform.api.model.CouponSendRecordApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/12/1.
 */
public interface CouponSendRecordAdminService {
    void deleteById(int id);
    int insertCouponSendRecord(CouponSendRecordApi record);
    CouponSendRecordApi selectById(int id);
    int updateCouponSendRecord(CouponSendRecordApi record);
    List<CouponSendRecordApi> selectListByPage(HashMap<String, Object> offsetLimit);
    int getCount();
    List<CouponSendRecordApi> selectByOpenId(Integer couponActiveId,String openid);
    List<CouponSendRecordApi> selectByActiveId(Integer couponActiveId);
    int getCountByOpenId(Integer couponActiveId,String openid);
}
