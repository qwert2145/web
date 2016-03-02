package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.QrcodeApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface QrcodeAdminService {
    void deleteById(int id);
    int insertQrcode(QrcodeApi record);
    QrcodeApi selectById(int id);
    int updateQrcode(QrcodeApi record);
    List<QrcodeApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();
    List<QrcodeApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);

    QrcodeApi selectByAccountId(int accountId);
}
