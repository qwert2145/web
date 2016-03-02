package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.MsgTemplateApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface MsgTemplateAdminService {
    void deleteById(int id);
    int insertMsgTemplate(MsgTemplateApi record);
    MsgTemplateApi selectById(int id);
    int updateMsgTemplate(MsgTemplateApi record);
    List<MsgTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();
    List<MsgTemplateApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);
}
