package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.ImageTemplateAdminService;
import com.womai.platform.api.model.ImageTemplateApi;
import com.womai.platform.api.service.ImageTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/11/30.
 */
public class ImageTemplateAdminServiceImpl implements ImageTemplateAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageTemplateAdminServiceImpl.class);
    private ImageTemplateService imageTemplateSoaService;

    public ImageTemplateService getImageTemplateSoaService() {
        return imageTemplateSoaService;
    }

    public void setImageTemplateSoaService(ImageTemplateService imageTemplateSoaService) {
        this.imageTemplateSoaService = imageTemplateSoaService;
    }

    @Override
    public void deleteById(int id) {
        imageTemplateSoaService.deleteById(id);
    }

    @Override
    public int insertImageTemplate(ImageTemplateApi record) {
        imageTemplateSoaService.insertImageTemplate(record);
        return 0;
    }

    @Override
    public ImageTemplateApi selectById(int id) {
        return imageTemplateSoaService.selectById(id);
    }

    @Override
    public int updateImageTemplate(ImageTemplateApi record) {
        return imageTemplateSoaService.updateImageTemplate(record);
    }

    @Override
    public List<ImageTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return imageTemplateSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public List<ImageTemplateApi> selectByAccountId(int accountId) {
        return imageTemplateSoaService.selectByAccountId(accountId);
    }

    @Override
    public int getCount() {
        return imageTemplateSoaService.getCount();
    }

    @Override
    public List<ImageTemplateApi> query(HashMap<String, Object> queryMap) {
        return imageTemplateSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return imageTemplateSoaService.queryCount(queryMap);
    }
}
