package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.NewsArticleTemplateAdminService;
import com.womai.platform.api.model.NewsArticleTemplateApi;
import com.womai.platform.api.service.NewsArticleTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class NewsArticleTemplateAdminServiceImpl implements NewsArticleTemplateAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsArticleTemplateAdminServiceImpl.class);
    private NewsArticleTemplateService newsArticleTemplateSoaService;

    public NewsArticleTemplateService getNewsArticleTemplateSoaService() {
        return newsArticleTemplateSoaService;
    }

    public void setNewsArticleTemplateSoaService(NewsArticleTemplateService newsArticleTemplateSoaService) {
        this.newsArticleTemplateSoaService = newsArticleTemplateSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertNewsArticleTemplate(NewsArticleTemplateApi record) {
        newsArticleTemplateSoaService.insertNewsArticleTemplate(record);
        return 0;
    }

    @Override
    public NewsArticleTemplateApi selectById(int id) {
        return newsArticleTemplateSoaService.selectById(id);
    }

    @Override
    public int updateNewsArticleTemplate(NewsArticleTemplateApi record) {
        newsArticleTemplateSoaService.updateNewsArticleTemplate(record);
        return 0;
    }

    @Override
    public List<NewsArticleTemplateApi> selectList() {
        return newsArticleTemplateSoaService.selectList();
    }

    @Override
    public List<NewsArticleTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return newsArticleTemplateSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount(int newsTemplateId) {
        return newsArticleTemplateSoaService.getCount(newsTemplateId);
    }

    @Override
    public List<NewsArticleTemplateApi> selectByTemplateId(int templateId) {
        return newsArticleTemplateSoaService.selectByTemplateId(templateId);
    }
}
