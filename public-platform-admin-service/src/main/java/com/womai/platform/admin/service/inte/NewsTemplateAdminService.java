package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.NewsTemplateApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface NewsTemplateAdminService {

    void deleteById(int id);
    int insertNewsTemplate(NewsTemplateApi record);
    NewsTemplateApi selectById(int id);
    int updateNewsTemplate(NewsTemplateApi record);
    List<NewsTemplateApi> selectList();
    List<NewsTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();

    List<NewsTemplateApi> selectByType(String articleType,Integer accountId);
    List<NewsTemplateApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);
}
