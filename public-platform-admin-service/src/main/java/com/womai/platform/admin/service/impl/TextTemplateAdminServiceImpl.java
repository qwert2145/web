package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.TextTemplateAdminService;
import com.womai.platform.api.model.TextTemplateApi;
import com.womai.platform.api.service.TextTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class TextTemplateAdminServiceImpl implements TextTemplateAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextTemplateAdminServiceImpl.class);
    private TextTemplateService textTemplateSoaService;

    public TextTemplateService getTextTemplateSoaService() {
        return textTemplateSoaService;
    }

    public void setTextTemplateSoaService(TextTemplateService textTemplateSoaService) {
        this.textTemplateSoaService = textTemplateSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertTextTemplate(TextTemplateApi record) {
        textTemplateSoaService.insertTextTemplate(record);
        return 0;
    }

    @Override
    public TextTemplateApi selectById(int id) {
        return textTemplateSoaService.selectById(id);
    }

    @Override
    public int updateTextTemplate(TextTemplateApi record) {
        textTemplateSoaService.updateTextTemplate(record);
        return 0;
    }

    @Override
    public List<TextTemplateApi> selectList() {
        return textTemplateSoaService.selectList();
    }

    @Override
    public List<TextTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return textTemplateSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return textTemplateSoaService.getCount();
    }

    @Override
    public List<TextTemplateApi> selectByAccountId(int accountId) {
        return textTemplateSoaService.selectByAccountId(accountId);
    }

    @Override
    public List<TextTemplateApi> query(HashMap<String, Object> queryMap) {
        return textTemplateSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return textTemplateSoaService.queryCount(queryMap);
    }
}
