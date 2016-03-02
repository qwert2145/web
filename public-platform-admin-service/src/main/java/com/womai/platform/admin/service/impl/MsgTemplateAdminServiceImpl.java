package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.MsgTemplateAdminService;
import com.womai.platform.api.model.MsgTemplateApi;
import com.womai.platform.api.service.MsgTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class MsgTemplateAdminServiceImpl implements MsgTemplateAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTemplateAdminServiceImpl.class);
    private MsgTemplateService msgTemplateSoaService;

    public MsgTemplateService getMsgTemplateSoaService() {
        return msgTemplateSoaService;
    }

    public void setMsgTemplateSoaService(MsgTemplateService msgTemplateSoaService) {
        this.msgTemplateSoaService = msgTemplateSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertMsgTemplate(MsgTemplateApi record) {
        msgTemplateSoaService.insertMsgTemplate(record);
        return 0;
    }

    @Override
    public MsgTemplateApi selectById(int id) {
        return msgTemplateSoaService.selectById(id);
    }

    @Override
    public int updateMsgTemplate(MsgTemplateApi record) {
        msgTemplateSoaService.updateMsgTemplate(record);
        return 0;
    }


    @Override
    public List<MsgTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return msgTemplateSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return msgTemplateSoaService.getCount();
    }

    @Override
    public List<MsgTemplateApi> query(HashMap<String, Object> queryMap) {
        return msgTemplateSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return msgTemplateSoaService.queryCount(queryMap);
    }
}
