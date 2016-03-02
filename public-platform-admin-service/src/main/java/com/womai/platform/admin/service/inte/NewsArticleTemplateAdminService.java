package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.NewsArticleTemplateApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface NewsArticleTemplateAdminService {
    void deleteById(int id);
    int insertNewsArticleTemplate(NewsArticleTemplateApi record);
    NewsArticleTemplateApi selectById(int id);
    int updateNewsArticleTemplate(NewsArticleTemplateApi record);
    List<NewsArticleTemplateApi> selectList();
    List<NewsArticleTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount(int newsTemplateId);

    List<NewsArticleTemplateApi> selectByTemplateId(int templateId);
}
