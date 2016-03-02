package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.TextTemplateApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface TextTemplateAdminService {
    void deleteById(int id);
    int insertTextTemplate(TextTemplateApi record);
    TextTemplateApi selectById(int id);
    int updateTextTemplate(TextTemplateApi record);
    List<TextTemplateApi> selectList();
    List<TextTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();
    List<TextTemplateApi> selectByAccountId(int accountId);
    List<TextTemplateApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);
}
