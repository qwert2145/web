package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.NewsTemplateAdminService;
import com.womai.platform.api.model.NewsTemplateApi;
import com.womai.platform.api.service.NewsTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class NewsTemplateAdminServiceImpl implements NewsTemplateAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsTemplateAdminServiceImpl.class);
    private NewsTemplateService newsTemplateSoaService;

    public NewsTemplateService getNewsTemplateSoaService() {
        return newsTemplateSoaService;
    }

    public void setNewsTemplateSoaService(NewsTemplateService newsTemplateSoaService) {
        this.newsTemplateSoaService = newsTemplateSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertNewsTemplate(NewsTemplateApi record) {
        newsTemplateSoaService.insertNewsTemplate(record);
        return 0;
    }

    @Override
    public NewsTemplateApi selectById(int id)
    {
        return newsTemplateSoaService.selectById(id);
    }

    @Override
    public int updateNewsTemplate(NewsTemplateApi record) {
        newsTemplateSoaService.updateNewsTemplate(record);
        return 0;
    }

    @Override
    public List<NewsTemplateApi> selectList() {
        return newsTemplateSoaService.selectList();
    }

    @Override
    public List<NewsTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return newsTemplateSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return newsTemplateSoaService.getCount();
    }

    @Override
    public List<NewsTemplateApi> selectByType(String articleType,Integer accountId) {
        return newsTemplateSoaService.selectByType(articleType,accountId);
    }

    @Override
    public List<NewsTemplateApi> query(HashMap<String, Object> queryMap) {
        return newsTemplateSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return newsTemplateSoaService.queryCount(queryMap);
    }
}
